package stanly.server.GitProject.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import stanly.server.GitProject.DAO.CommitterDAO;
import stanly.server.GitProject.Git.GitControl;
import stanly.server.GitProject.Git.FileName.FilenameCutter;
import stanly.server.GitProject.Model.ProjectInfo;

/**
 * @author Karuana
 *	Git을 제어하는 서비스이다. 
 */
@Service("gitControlService")
public class GitControlService {

	protected static final Logger logger = Logger.getLogger("GitService");
	@Autowired
	private ProjectInfoService projectService;
	
	@Autowired
	private CommitterDAO committerDAO;
	
	/**
	 * 비동기 처리를 하는 부분 Git 클론을 받는다. 
	 * @param uRL
	 * @param ProjectName
	 * @return Future<GitControl> Clone이 다되면 프로젝트 정보를 리턴한다.
	 */
	@Async
	public Future<GitControl> GitClone(String uRL,String ProjectName)
	{
	
		GitControl Git=null;
		try{
			 Git= new GitControl(uRL,ProjectName);
			logger.info("GitCloneing");
			String path = Git.Clone();
			ProjectInfo project = null;
			
			RevWalk Temp = Git.getCommitData();
			HashMap<String,ArrayList<String>> CrashBoard = new HashMap<String,ArrayList<String>>();
			RevCommit oldCommit=null;
			
			for (RevCommit rev =Temp.next();rev !=null;rev=Temp.next()){
		    	 		
					org.eclipse.jgit.lib.PersonIdent Test1 = rev.getAuthorIdent();
					Date time  = new Date((long)rev.getCommitTime()*1000);
					//처음 반복시 프로젝트를 생성한다. 
					if(project==null) project = projectService.addProject(uRL, path, ProjectName, time);
					
					//커밋 시점에서 작성자 분류할 필요 있음 
					projectService.addCommit(project, time, rev.getFullMessage(),Test1.getName());
					addDiff(Git,oldCommit,rev,CrashBoard);
					oldCommit=rev;
			}
			logger.info("Commit Change List");
		     // 커밋 리스트 끝나고 변경 이력 넣기
			Iterator<String> it = CrashBoard.keySet().iterator();
			while(it.hasNext())
			{
				String Key = it.next();
				logger.info(Key);
				committerDAO.insertCommitter(Key, project);
				List<String> list = CrashBoard.get(Key);
				for(String Influence: list)
				{
					committerDAO.insertCommitterInfluence(Key, Influence, project);
				}
			}
			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
	
		}
		return new AsyncResult<GitControl>(Git);
	}
	
	public void addDiff(GitControl Git,RevCommit old, RevCommit now,HashMap<String,ArrayList<String>> Crash){
		if(Git!=null)
		{
			try {
				List<DiffEntry> Data = Git.getDiffList(old,now);
				org.eclipse.jgit.lib.PersonIdent UserID = now.getAuthorIdent();
				if(!Crash.containsKey(UserID.getName()))
				{
					Crash.put(UserID.getName(), new ArrayList<String>());
				}
				
				for(int i=0;i<Data.size();i++)
				{
					if(FilenameCutter.IsJavaFile(Data.get(i).getNewPath()))
					{
							String ClassName =FilenameCutter.GetFileName(Data.get(i).getNewPath());
							if(!Crash.get(UserID.getName()).contains(ClassName))
							{
								//Diff 파일 추가 	
								Crash.get(UserID.getName()).add(ClassName);
							}
					}
				}
				
			} catch (IncorrectObjectTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
