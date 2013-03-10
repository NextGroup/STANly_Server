package stanly.server.GitProject.Service;

import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import stanly.server.GitProject.Git.GitControl;

@Service("gitControlService")
public class GitControlService {

	protected static final Logger logger = Logger.getLogger("ProjectInfoservice");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Async
	public Future<GitControl> GitClone(String uRL,String ProjectName)
	{
	
		GitControl Git=null;
		try{
			 Git= new GitControl(uRL,ProjectName);
			logger.info("Test");
			Git.Clone();
			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
	
		}
		return new AsyncResult<GitControl>(Git);
	}
	
	
}
