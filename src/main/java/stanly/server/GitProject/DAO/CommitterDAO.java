package stanly.server.GitProject.DAO;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.GitProject.Model.committer.CommitterInfluence;
import stanly.server.GitProject.Model.committer.ProjectCommitter;
@Repository
@Transactional
public class CommitterDAO {
	protected static final Logger logger = Logger.getLogger("CommitterDAO");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	public ProjectCommitter insertCommitter(String committer, ProjectInfo pInfo)
	{
		ProjectCommitter data = null;
		try{
			logger.info("ProjectInfo insert");
			Session session = sessionFactory.getCurrentSession();
			
			data = new ProjectCommitter(committer.toLowerCase(),pInfo);
			// Save
			session.save(data);
	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		return data;
	}
	
	public CommitterInfluence insertCommitterInfluence(String committer, String influenceClass,
			ProjectInfo pInfo)
	{
		CommitterInfluence data = null;
		try{
			logger.info("ProjectInfo insert");
			Session session = sessionFactory.getCurrentSession();
			
			data = new CommitterInfluence(committer,influenceClass,pInfo);
			// Save
			session.save(data);
	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		return data;
	}
}
