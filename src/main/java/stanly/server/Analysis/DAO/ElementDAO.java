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

import stanly.server.Analysis.Model.ProjectElementNode;
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
	
	
	public ProjectElementNode insertElement(ProjectElementNode node)
	{		

		try{

			Session session = sessionFactory.getCurrentSession();
			
			NodeType type = node.getType();
			
			session.save(node);
			
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
					case ANNOTATION:
					case INTERFACE:
					case ENUM:
						session.save((ClassMetric)node.getEMetric());
						break;
					case FIELD:
						session.save((AttributeMetric)node.getEMetric());
						break;
					case METHOD:
					case CONSTRUCTOR:
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
	
	public List<ProjectElementNode> getNodeTree(ProjectCommit CommitID)
	{
		List nList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion projectEq = Restrictions.eq("commit", CommitID);
			 Criteria crit = session.createCriteria(ProjectElementNode.class);
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

	public ProjectElementNode getNode(ProjectCommit CommitID, String projectName)
	{
		ProjectElementNode nList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion commitEq = Restrictions.eq("commit", CommitID);
			 Criterion  projectEq = Restrictions.eq("Name", projectName);
			 Criteria crit = session.createCriteria(ProjectElementNode.class);
			 crit.add(commitEq);
			 crit.add(projectEq);
			 nList = (ProjectElementNode)crit.uniqueResult();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return nList;
	}
	public List<ProjectElementNode> getSubNodeTree(ProjectCommit CommitID,String ParentNode)
	{
		List nList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion projectEq = Restrictions.eq("commit", CommitID);
		
			 ProjectElementNode MainNode = getNode(CommitID,ParentNode);
			 if(MainNode!=null)
			 {
				 
				 Criterion Left = Restrictions.ge("Name", new Integer(MainNode.getNSLeft()));
				 Criterion Right = Restrictions.le("Name", new Integer(MainNode.getNSRight()));
				 Criteria cr = session.createCriteria(ProjectElementNode.class);
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
	public void DataFlush()
	{
		Session session = sessionFactory.getCurrentSession();
		session.flush();
	}
}
