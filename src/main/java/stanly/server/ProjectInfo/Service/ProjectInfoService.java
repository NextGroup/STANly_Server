package stanly.server.ProjectInfo.Service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.ProjectInfo.Model.ProjectInfo;

@Service("projectinfoService")
@Transactional
public class ProjectInfoService {

	protected static final Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public boolean add(String uRL, String location, String name)
	{
		try{
			logger.info("ProjectInfo insert");
			Session session = sessionFactory.getCurrentSession();
			ProjectInfo data = new ProjectInfo(uRL,location,name);
	
			// Save
			session.save(data);
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	public ProjectInfo getProjectInfo(String Name)
	{
		ProjectInfo Data =null;
		try{
			logger.info("ProjectInfo select");
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM  PROJECT_INFO whare PROJECT_NAME == '"+Name+"'");
			List<ProjectInfo> list = query.list();
			if(list.size()!=0)
				Data = list.get(0);
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		return Data;
	}
	
	
}
