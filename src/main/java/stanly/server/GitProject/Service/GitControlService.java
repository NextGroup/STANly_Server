package stanly.server.GitProject.Service;


import java.util.Date;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import stanly.server.GitProject.Git.GitControl;
import stanly.server.GitProject.Model.ProjectInfo;

@Service("gitControlService")
public class GitControlService {

	protected static final Logger logger = Logger.getLogger("GitService");
	@Autowired
	private ProjectInfoService projectService;
	
	@Async
	public Future<GitControl> GitClone(String uRL,String ProjectName)
	{
	
		GitControl Git=null;
		try{
			 Git= new GitControl(uRL,ProjectName);
			logger.info("GitCloneing");
			String path = Git.Clone();
			ProjectInfo project = projectService.addProject(uRL, path, ProjectName);
			
			Iterable<RevCommit> Temp = Git.getCommand().all().call();
	
		     for (RevCommit rev : Temp) {
		    	 		
					org.eclipse.jgit.lib.PersonIdent Test1 = rev.getAuthorIdent();
					Date time  = new Date((long)rev.getCommitTime()*1000);
					
					projectService.addCommit(project, time, rev.getFullMessage(),Test1.getName());

			}
  
			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
	
		}
		return new AsyncResult<GitControl>(Git);
	}
	
	
}
