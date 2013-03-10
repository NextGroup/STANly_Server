package stanly.server.GitProject.DAO;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;

@Repository
@Transactional
public class ProjectDAO {

	protected static final Logger logger = Logger.getLogger("ProjectDAO");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	
	/**
	 *  Project를 생성한다.
	 * @param uRL
	 * @param location
	 * @param name
	 * @return
	 */
	public ProjectInfo addProject(String uRL, String location, String name)
	{
		ProjectInfo data = null;
		try{
			logger.info("ProjectInfo insert");
			Session session = sessionFactory.getCurrentSession();
			
			data = new ProjectInfo(uRL,location,name);
			// Save
			session.save(data);
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		return data;
	}
	
	public List<ProjectInfo> getPrjectList()
	{
		List<ProjectInfo> projectList = null;
		
		try{
			Session session = sessionFactory.getCurrentSession();
			
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
		
			 Criteria crit = session.createCriteria(ProjectInfo.class);

			 crit.addOrder(Order.desc("pid"));
			 projectList = crit.list();
			  
		}catch(Exception e)
		{
			logger.error(e);
			return null;
		}
	
		return projectList;
	}
	
	public ProjectInfo getProjectInfo(String ProjectName)
	{
		ProjectInfo projectList = null;
		
		try{
			Session session = sessionFactory.getCurrentSession();
			
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
		
			 Criteria crit = session.createCriteria(ProjectInfo.class);
			 Criterion projectEq = Restrictions.eq("name", ProjectName ); 
			 crit.add(projectEq);
			 crit.addOrder(Order.desc("pid"));
			 projectList = (ProjectInfo) crit.uniqueResult();

		}catch(Exception e)
		{
			logger.error(e);
			return null;
		}
		return projectList;
	}
	
	/**
	 * 해당하는 GitURL에 해당하는 프로젝트가 있는지 확인 
	 * @param gitURL
	 * @return boolean
	 */
	public ProjectInfo getProjectWithGitURL(String gitURL)
	{
		ProjectInfo projectList = null;
		
		try{
			Session session = sessionFactory.getCurrentSession();
			
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
		
			 Criteria crit = session.createCriteria(ProjectInfo.class);
			 Criterion projectEq = Restrictions.eq("URL", gitURL ); 
			 crit.add(projectEq);
			 projectList = (ProjectInfo) crit.uniqueResult();
			  
		}catch(Exception e)
		{
			logger.error(e);
			return null;
		}
		return projectList;
	}
	//Commit 관련 
	public ProjectCommit addCommit(ProjectInfo project, Date updateDate, String message, 
			String author)
	{
	
		ProjectCommit commit = null;
		try{
			logger.info("add Commit");
			Session session = sessionFactory.getCurrentSession();
			
			
			commit = new ProjectCommit(updateDate, message ,author);
			commit.setProjectInfo(project); 
			// Save
			session.save(commit);
	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return commit;
		}
		return commit;
	}
	/**
	 * 마지막 커밋 정보를 리턴한다. 
	 * @param projectName
	 * @return
	 */
	public ProjectCommit getLastCommit(ProjectInfo project)
	{
		ProjectCommit LastCommit=null;
		try{
			logger.info("Get Commit List");
			Session session = sessionFactory.getCurrentSession();
			
			 Criteria crit = session.createCriteria(ProjectCommit.class);
			 Criterion projectEq = Restrictions.eq("PInfo", project ); 
			 crit.add(projectEq);
			 crit.addOrder(Order.desc("UpdateDate"));
			 LastCommit = (ProjectCommit) crit.list().get(0);
			
		}catch(Exception e)
		{
			logger.error(e);
			return LastCommit;
		}
		return LastCommit;
	}
	/**
	 * 	해당하는 프로젝트 이름의 Commit 리스트를 반환한다. 
	 * @param projectName
	 * @return
	 */
	public List<ProjectCommit> getCommitList(String projectName)
	{
		ProjectInfo info = getProjectInfo(projectName);
		List<ProjectCommit> commitList=null;
		try{
			logger.info("Get Commit List");
			Session session = sessionFactory.getCurrentSession();
			
			 Criteria crit = session.createCriteria(ProjectCommit.class);
			 Criterion projectEq = Restrictions.eq("PInfo", info ); 
			 crit.add(projectEq);
			 crit.addOrder(Order.desc("UpdateDate"));
			 commitList = crit.list();
			
		}catch(Exception e)
		{
			logger.error(e);
			return commitList;
		}
		
		return commitList;
	}
	
	public boolean IsProject(String gitURL, String ProjectName)
	{
		List<ProjectInfo> projectList = null;
		
		try{
			Session session = sessionFactory.getCurrentSession();
			
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
		
			 Criteria crit = session.createCriteria(ProjectInfo.class);
			 Criterion projectEq = Restrictions.eq("URL", gitURL ); 
			 Criterion NameEq = Restrictions.eq("name", ProjectName ); 
			 crit.add(Restrictions.or(projectEq, NameEq));
			 
			 projectList = crit.list();
			  
		}catch(Exception e)
		{
			logger.error(e);
			return false;
		}
		return (projectList.size() == 0) ? true: false;
	}
	
	public List<ProjectInfo> getProjectList()
	{

		List<ProjectInfo> ProjectList=null;
		try{
			logger.info("Get Commit List");
			Session session = sessionFactory.getCurrentSession();
			
			 Criteria crit = session.createCriteria(ProjectInfo.class);
			 crit.addOrder(Order.desc("pid"));
			 ProjectList = crit.list();
			
		}catch(Exception e)
		{
			logger.error(e);
			return ProjectList;
		}
		
		return ProjectList;
	}
	
}
