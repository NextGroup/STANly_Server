package stanly.server.MetricView.DAO;

import java.util.List;

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
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;

@Repository
@Transactional
public class ElementSearchDAO {
	protected static final Logger logger = Logger.getLogger("ElementDAO - Search");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public ProjectElementNode getProjectNode(ProjectCommit commit)
	{
		ProjectElementNode  rootNode =null;
		try{
			Session session = sessionFactory.getCurrentSession();
			
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion CommitEq = Restrictions.eq("commit", commit);
			 Criterion projectEq = Restrictions.eq("NSLeft", new Integer(1)); //NSLeft == 1 이면 프로젝트 노
			 Criteria crit = session.createCriteria(ProjectElementNode.class);
			 crit.add(CommitEq);
			 logger.info(commit.getMessage()+commit.getCommitid());
			 logger.info(crit.list().size());
			
			 crit.add(projectEq);
			 rootNode = (ProjectElementNode) crit.uniqueResult();
			 
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		
		return rootNode;
	}
	
	public List<ProjectElementNode> getChildNode(String ParentName,int NSleft, int NSRight, NodeType type, ProjectCommit commit)
	{
		List<ProjectElementNode> nodeList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion CommitEq = Restrictions.eq("commit", commit);
			 Criterion parentEq = Restrictions.eq("ParetnName", ParentName);
			 Criterion nodeEq= null;
			 if(type == NodeType.PACKAGESET)
				 nodeEq= Restrictions.or(Restrictions.eq("type", NodeType.PACKAGESET), Restrictions.eq("type", NodeType.PACKAGE));

			 Criterion Left = Restrictions.ge("NSLeft", new Integer(NSleft+1));
			 Criterion Right = Restrictions.le("NSRight", new Integer(NSRight));
			 Criteria crit = session.createCriteria(ProjectElementNode.class);
			 crit.add(CommitEq);
			 crit.add(parentEq);
			 crit.add(Left);
			 crit.add(Right);
			 if(nodeEq != null)
				 crit.add(nodeEq);
			 nodeList =  crit.list();
		
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return nodeList;
		}
		
		return nodeList;
		
	}
	
	public ProjectElementNode getElementNode(ProjectCommit commit, int NSLeft)
	{
		ProjectElementNode  SeletedNode =null;
		try{
			Session session = sessionFactory.getCurrentSession();
			
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion CommitEq = Restrictions.eq("commit", commit);
			 Criterion projectEq = Restrictions.eq("NSLeft", NSLeft); //NSLeft == 1 이면 프로젝트 노
			 Criteria crit = session.createCriteria(ProjectElementNode.class);
			 crit.add(projectEq);
			 crit.add(CommitEq);
			
			 SeletedNode = (ProjectElementNode) crit.uniqueResult();
			 logger.info(SeletedNode.getName());
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		
		return SeletedNode;
	}
	
	
}