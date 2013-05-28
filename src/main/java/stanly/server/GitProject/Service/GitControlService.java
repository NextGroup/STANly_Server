package stanly.server.GitProject.Service;


import java.util.Date;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import stanly.server.GitProject.Git.GitControl;
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
			ProjectInfo project = projectService.addProject(uRL, path, ProjectName);
			
			RevWalk Temp = Git.getCommitData();
	
		     for (RevCommit rev =Temp.next();rev !=null;rev=Temp.next()){
		    	 		
					org.eclipse.jgit.lib.PersonIdent Test1 = rev.getAuthorIdent();
					Date time  = new Date((long)rev.getCommitTime()*1000);
					//커밋 시점에서 작성자 분류할 필요 있음 
					projectService.addCommit(project, time, rev.getFullMessage(),Test1.getName());

			}
		     // 커밋 리스트 끝나고 변경 이력 넣기
			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
	
		}
		return new AsyncResult<GitControl>(Git);
	}
	
	
}
