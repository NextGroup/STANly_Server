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
import stanly.server.GitProject.Model.committer.CommitterInfluence;
import stanly.server.GitProject.Model.committer.ProjectCommitter;

/**
 * @author Karuana
 *	프로젝트 정보를 관리하는 서비스이다. 
 */
@Service("projectinfoService")
@Transactional
public class ProjectInfoService {

	protected static final Logger logger = Logger.getLogger("ProjectInfoservice");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private ProjectDAO project;
	
	/**
	 * 프로젝트를 추가한다. 
	 * @param uRL
	 * @param location
	 * @param name
	 * @return
	 */
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
	
	/**
	 * Commit을 추가한다. 
	 * @param info
	 * @param updateDate
	 * @param message
	 * @param author
	 * @return
	 */
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
	
	
	/**
	 * 작성해야 하는 부분 
	 * @return
	 */
	public ProjectCommitter addCommitter()
	{
		return null;
	}
	
	public CommitterInfluence addInfluence()
	{
		return null;
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
	
	
	
	/**
	 * 전체 커밋 정보를 얻어온다. 
	 * @param ProjectName
	 * @return
	 */
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
	
	/**
	 * 마지막 날짜 커밋 정보를 가져온다.
	 * @param Project
	 * @return
	 */
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
	
	/**
	 * 해당하는 프로젝트가 존재하는지에 대한 상태 정보를 리턴한다. 
	 * @param Url
	 * @param name
	 * @return
	 */
	public ProjectState getProjectState(String Url, String name)
	{
		return project.getProjectState(Url, name);
	}
	
}
