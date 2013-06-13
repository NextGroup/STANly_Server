package stanly.server.CommonView.DAO;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.CommonView.JSON.LinkedDeveloper;
import stanly.server.GitProject.Model.ProjectCommit;

@Repository
@Transactional
public class LinkedDeveloperDAO {

	protected static final Logger logger = Logger.getLogger("LinkedDeveloper");
	/**
	 * 하이버네이트 연결을 위한 세션을 만들어주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private String sprite(String path)
	{
		String[]  arr =  path.split("\\.");
		logger.info((arr.length>1) ?  arr[arr.length-1]:path);
		return (arr.length>1) ?  arr[arr.length-1]:path;
	}
	

	
	public LinkedDeveloper getLinkedDeveloper(ProjectCommit commit, ProjectElementNode node)
	{
		LinkedDeveloper dev = new LinkedDeveloper();
		try{
			//NSleft
			Session session = sessionFactory.getCurrentSession();
		
		}catch(Exception e)
		{
			logger.error(e);
		}
		
		return null;
	}
	
	
}
