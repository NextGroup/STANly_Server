package stanly.server.Analysis.DAO;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Relation.NodeRelation;
import stanly.server.GitProject.Model.ProjectCommit;

@Repository
@Transactional
public class RelationDAO {
	protected static final Logger logger = Logger.getLogger("RelationDAO");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public NodeRelation insertRelation(NodeRelation relation)
	{
		try{

			Session session = sessionFactory.getCurrentSession();
			session.save(relation);
		
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		
		return relation;
	}
	
	/**
	 * 주어진 커밋 아이디와 Src이름에 해당하는 릴레이션 객체들을 가져온다.
	 * @param CommitID
	 * @param SrcName
	 * @return
	 */
	public List<NodeRelation> getSrcRelation(ProjectCommit CommitID, String SrcName)
	{
		List<NodeRelation> NodeRList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion commitEq = Restrictions.eq("commit", CommitID);
			 Criterion  SrcEq = Restrictions.eq("SrcName", SrcName);
			 Criteria crit = session.createCriteria(NodeRelation.class);
			 crit.add(commitEq);
			 crit.add(SrcEq);
			 NodeRList = crit.list();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return NodeRList;
	}
	/**
	 * @param CommitID
	 * @param TarName
	 * @return
	 */
	public List<NodeRelation> getTarRelation(ProjectCommit CommitID, String TarName)
	{
		List<NodeRelation> NodeRList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion commitEq = Restrictions.eq("commit", CommitID);
			 Criterion  TarEq = Restrictions.eq("TarName", TarName);
			 Criteria crit = session.createCriteria(NodeRelation.class);
			 crit.add(commitEq);
			 crit.add(TarEq);
			 NodeRList = crit.list();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return NodeRList;
	}
	
	public List<NodeRelation> getSrcLikeRelation(ProjectCommit CommitID, String SrcName)
	{
		List<NodeRelation> NodeRList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion commitEq = Restrictions.eq("commit", CommitID);
			 Criterion  Srclike = Restrictions.like("SrcName", SrcName,MatchMode.START);
			 Criteria crit = session.createCriteria(NodeRelation.class);
			 crit.add(commitEq);
			 crit.add(Srclike);
			 NodeRList = crit.list();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return NodeRList;
	}
	
	public List<NodeRelation> getTarLikeRelation(ProjectCommit CommitID, String TarName)
	{
		List<NodeRelation> NodeRList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion commitEq = Restrictions.eq("commit", CommitID);
			 Criterion  Tarlike = Restrictions.like("TarName", TarName,MatchMode.START);
			 Criteria crit = session.createCriteria(NodeRelation.class);
			 crit.add(commitEq);
			 crit.add(Tarlike);
			 NodeRList = crit.list();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return NodeRList;
	}
}
