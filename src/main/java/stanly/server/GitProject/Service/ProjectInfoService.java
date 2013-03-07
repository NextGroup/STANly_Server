package stanly.server.GitProject.Service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;

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
			ProjectCommit Data2 = new ProjectCommit(new Date(),"HHH" ,"Hell",data);
			data.addCommit(Data2);
			// Save
			session.save(data);
			
			session.save(Data2);
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
			
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Query query = session.createQuery("FROM ProjectInfo where name = :code");
			query.setParameter("code", Name);
			List<ProjectInfo> list = query.list();
			logger.error("GetData Size:"+list.size());
			if(list.size()!=0)
			{
				Data = list.get(0);
				
				
				Object Datas[] = Data.getCommitList().toArray();
				for(int i=0;i<Datas.length;i++)
				{
					logger.error("Datas[i]"+((ProjectCommit)Datas[i]).getAuthor());
					logger.error("TEST ddddd:"+ ((ProjectCommit)Datas[i]).getProjectInfo().getName());
				}
			}
			
		
			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		logger.error("Test "+Data.getName());
	
		return Data;
	}
	
	public List getCommitList()
	{
		return null;
	}
}
