package stanly.server.Analysis.DAO;

import java.util.ArrayList;
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

import stanly.server.Analysis.Model.ElementNode;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;

@Repository
@Transactional
public class ElementDAO {
	protected static final Logger logger = Logger.getLogger("ElementDAO");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	public ElementNode insertElement(ElementNode node)
	{		

		try{

			Session session = sessionFactory.getCurrentSession();
	
			session.save(node);
			

		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		
		return null;
	}
	
	public List<ElementNode> getNodeTree(ProjectCommit CommitID)
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

}
