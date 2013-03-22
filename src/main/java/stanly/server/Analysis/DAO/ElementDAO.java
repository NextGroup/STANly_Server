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

/**
 * @author Karuana
 * 실제적으로 DB에 접근하는 DAO 객체로 ProjectElement에 접근한다.
 */
/**
 * @author Karuana
 *
 */
/**
 * @author Karuana
 *
 */
@Repository
@Transactional
public class ElementDAO {
	protected static final Logger logger = Logger.getLogger("ElementDAO");
	
	/**
	 *  하이버네이트 연결을 위한 세션을 생성해주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * 입력받은 객체를 저장한다. 
	 * @param node 저장할 객체 
	 * @return 저장한 객체를 리턴한다.
	 */
	public ProjectElementNode insertElement(ProjectElementNode node)
	{		

		try{

			Session session = sessionFactory.getCurrentSession();
			
			NodeType type = node.getType();
			//일단 노드 객체를 저장한다.
			session.save(node);
			
			if(node.getEMetric()!=null)
			{
				logger.info("Node Metric"+node.getEMetric());
				//노드에 해당하는 Metric 객체를 저장한다.
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
	
	/**
	 * 프로젝트 커밋에 해당하는 전체 ElementNode의 트리를 리턴한다.
	 * @param CommitID
	 * @return 프로젝트 트리를 리턴한다.
	 */
	public List<ProjectElementNode> getNodeTree(ProjectCommit CommitID)
	{
		List nList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion projectEq = Restrictions.eq("commit", CommitID);
			 Criteria crit = session.createCriteria(ProjectElementNode.class);
			 crit.add(projectEq);
			 //Nested Set Model 방식으로 DB를 저장하기 때문에 Left에 따라서 정리하면 트리가 나온다.
			 crit.addOrder(Order.asc("NSLeft"));
			 nList = crit.list();
		

		}
		catch(Exception e)
		{
			logger.error(e);
			
		}
		return nList;
	}
	
	/**
	 *  주어진 커밋 시점에서 주어진 아이디와 매칭되는 노드를 검색한다.
	 * @param CommitID < 커밋 시점 
	 * @param projectName < 찾을 노드의 명
	 * @return 결과 노드 못찾으면 Null
	 */
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
	
	/**
	 * 주어진 부모 정보를 바탕으로 차일드, 자식 노드를 구한다.
	 * @param CommitID 커밋 시점 
	 * @param ParentNode 부모의 이름 
	 * @return 자식 리스트 
	 */
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
				 
				 Criterion Left = Restrictions.ge("NSLeft", new Integer(MainNode.getNSLeft()));
				 Criterion Right = Restrictions.le("NSRight", new Integer(MainNode.getNSRight()));
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
	
	/**
	 *	지금까지 변경 내용을 DB에 반영한다.  
	 */
	public void DataFlush()
	{
		Session session = sessionFactory.getCurrentSession();
		session.flush();
	}
}
