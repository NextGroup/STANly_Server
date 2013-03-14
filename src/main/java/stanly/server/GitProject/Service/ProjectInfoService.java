package stanly.server.GitProject.Service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.DAO.ProjectState;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;

@Service("projectinfoService")
@Transactional
public class ProjectInfoService {

	protected static final Logger logger = Logger.getLogger("ProjectInfoservice");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private ProjectDAO project;
	
	public ProjectInfo addProject(String uRL, String location, String name)
	{
		ProjectInfo PInfo;
		try{
			PInfo = project.getProjectWithGitURL(uRL);
			
			if(PInfo==null)
				PInfo = project.addProject(uRL, location, name);
			
	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		return PInfo;
	}
	
	public ProjectCommit addCommit(ProjectInfo info,Date updateDate, String message, String author)
	{
		ProjectCommit commit=null;
		try{
	
			commit = project.addCommit(info, updateDate, message, author);
			
	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		return commit;
	}
	
	
	public ProjectInfo getProjectInfo(String Name)
	{
		ProjectInfo Data =null;
		try{
		
			Data = project.getProjectInfo(Name);
			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		
	
		return Data;
	}
	
	
	
	public List<ProjectCommit> getCommitList(String ProjectName)
	{
		List<ProjectCommit> PList =null;
		try{
		 PList = project.getCommitList(ProjectName);
		
			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}

	
		return PList;
	}
	
	public ProjectCommit getLastCommit(ProjectInfo Project)
	{
		ProjectCommit commit =null;
		try{
			commit = project.getLastCommit(Project);
		
			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}

	
		return commit;
	}
	
	public ProjectState getProjectState(String Url, String name)
	{
		return project.getProjectState(Url, name);
	}
	
}
