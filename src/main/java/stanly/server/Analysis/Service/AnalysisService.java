package stanly.server.Analysis.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.ElementNode;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;

@Service("analysisService")
@Transactional
public class AnalysisService {

	protected static final Logger logger = Logger.getLogger("AnalysisService");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	public boolean AddNode(ProjectCommit commit)
	{
		
		//Test Case 만들기    
		try{

			Session session = sessionFactory.getCurrentSession();
			ArrayList<ElementNode> list = new ArrayList<ElementNode>();
			list.add(new ElementNode("stanly.server", "NONE", 1, 6,NodeType.PACKAGE));
			list.add(new ElementNode("stanly.server.Analysis", "stanly.server", 2, 3,NodeType.PACKAGE));
			list.add(new ElementNode("stanly.server.GitProject", "stanly.server", 4, 5,NodeType.PACKAGE));
			// Data Save
			for(int i=0;i<list.size();i++)
			{
				list.get(i).setCommit(commit);
				session.save(list.get(i));
			}

		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	public ProjectCommit getCommit(ProjectInfo ProjectID)
	{
		ProjectCommit data = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion projectEq = Restrictions.eq("PInfo", ProjectID);
			 Criteria crit = session.createCriteria(ProjectCommit.class);
			 crit.add(projectEq);
			 crit.addOrder(Order.desc("UpdateDate"));
			  data = (ProjectCommit) crit.uniqueResult();

		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			
		}
		return data;
		
	}
	public List getTree(ProjectCommit CommitID)
	{
		List nList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion projectEq = Restrictions.eq("commit", CommitID);
			 Criteria crit = session.createCriteria(ElementNode.class);
			 crit.add(projectEq);
			 crit.addOrder(Order.asc("NSLeft"));
			 nList = crit.list();
		

		}
		catch(Exception e)
		{
			logger.error(e);
			
		}
		return nList;
	}
	@Async
	public Future<String> analysisNode(ProjectCommit commit)
	{
		try{
			logger.info("ProjectInfo insert");
			Session session = sessionFactory.getCurrentSession();
			
			
			// Data Save
			session.save(null);

		}catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return new AsyncResult<String>("완료 ㅎㅎ");
	}
}
