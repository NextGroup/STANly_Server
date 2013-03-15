package stanly.server.MetricView.DAO;

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
import stanly.server.MetricView.Json.MartinMetricList;

@Repository
@Transactional
public class MetricSearchDAO {
	protected static final Logger logger = Logger.getLogger("MetricDAO - Search");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	private ProjectElementNode getElementNodeByType(ProjectCommit commit, NodeType type)
	{		
		ProjectElementNode  rootNode =null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion projectEq = Restrictions.eq("NSLeft", 1); //NSLeft == 1 이면 프로젝트 노
			Criteria crit = session.createCriteria(ProjectElementNode.class);
			crit.add(CommitEq);
			crit.add(projectEq);
			rootNode = (ProjectElementNode) crit.uniqueResult();
	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
	
		return rootNode;
	}
	public MartinMetricList getMertinValue()
	{
		
		return null;
	}
}
