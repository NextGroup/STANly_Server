package stanly.server.Analysis.DAO;

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
import stanly.server.Analysis.Model.Metric.AttributeMetric;
import stanly.server.Analysis.Model.Metric.ClassMetric;
import stanly.server.Analysis.Model.Metric.LibraryMetric;
import stanly.server.Analysis.Model.Metric.MethodMetric;
import stanly.server.Analysis.Model.Metric.PackageMetric;
import stanly.server.Analysis.Model.Metric.PackageSetMetric;
import stanly.server.Analysis.Model.Metric.ProjectMetric;
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
			
			NodeType type = node.getType();
			
			session.save(node);
			logger.info("Node is Null ? "+node);
			if(node.getEMetric()!=null)
			{
				logger.info("Node Metric"+node.getEMetric());
				switch(type)
				{
					case PROJECT:
						session.save((ProjectMetric)node.getEMetric());
						break;
					case LIBRARY:
						session.save((LibraryMetric)node.getEMetric());
						break;
					case PACKAGE:
						session.save((PackageMetric)node.getEMetric());
						break;
					case PACKAGESET:
						session.save((PackageSetMetric)node.getEMetric());
						break;
					case CLASS:
						session.save((ClassMetric)node.getEMetric());
						break;
					case FIELD:
						session.save((AttributeMetric)node.getEMetric());
						break;
					case METHOD:
						session.save((MethodMetric)node.getEMetric());
						break;
				}
			}

		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		
		return node;
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

	public ElementNode getNode(ProjectCommit CommitID, String projectName)
	{
		ElementNode nList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion commitEq = Restrictions.eq("commit", CommitID);
			 Criterion  projectEq = Restrictions.eq("Name", projectName);
			 Criteria crit = session.createCriteria(ElementNode.class);
			 crit.add(commitEq);
			 crit.add(projectEq);
			 nList = (ElementNode)crit.uniqueResult();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return nList;
	}
	public List<ElementNode> getSubNodeTree(ProjectCommit CommitID,String ParentNode)
	{
		List nList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion projectEq = Restrictions.eq("commit", CommitID);
		
			 ElementNode MainNode = getNode(CommitID,ParentNode);
			 if(MainNode!=null)
			 {
				 
				 Criterion Left = Restrictions.ge("Name", new Integer(MainNode.getNSLeft()));
				 Criterion Right = Restrictions.le("Name", new Integer(MainNode.getNSRight()));
				 Criteria cr = session.createCriteria(ElementNode.class);
				 cr.add(projectEq);
				 cr.add(Left);
				 cr.add(Right);
				 cr.addOrder(Order.asc("NSLeft"));
				 nList = cr.list();
				 
			 }
			 
		}
		catch(Exception e)
		{
			logger.error(e);
			
		}
		return nList;
	}
	
}
