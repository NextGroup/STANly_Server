package stanly.server.Analysis.DAO;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	 * 주어진 커밋 아이디와 Src이름에 해당하는 릴레이션 객체들을 가져온다. 20개 단위로 가져온다.
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
			 //같은 패키지 안에 있는 내옹은 제외한다.
			 Criterion tarEq = Restrictions.not( Restrictions.like("TarName", SrcName+"%"));
			 Criteria crit = session.createCriteria(NodeRelation.class);
			 crit.add(commitEq);
			 crit.add(SrcEq);
			 crit.add(tarEq);
			 crit.setMaxResults(20);
			
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
			 Criterion  Srclike = Restrictions.like("SrcName", SrcName+"%");
			 Criteria crit = session.createCriteria(NodeRelation.class);
			 crit.add(commitEq);
			 crit.add(Srclike);
			 NodeRList = crit.list();
			logger.info("Count List  "+NodeRList.size());
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
			 Criterion  Tarlike = Restrictions.like("TarName", TarName+"%");
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
	
	public List<NodeRelation> getLikeRelation(ProjectCommit CommitID, String SrcName,String TarName)
	{
		List<NodeRelation> NodeRList = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion commitEq = Restrictions.eq("commit", CommitID);
			 Criterion  Tarlike = Restrictions.like("TarName", TarName+"%");
			 Criterion  Srclike = Restrictions.like("SrcName", SrcName+"%");
			 Criteria crit = session.createCriteria(NodeRelation.class);
			 crit.add(commitEq);
			 crit.add(Tarlike);
			 crit.add(Srclike);
			 NodeRList = crit.list();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return NodeRList;
	}
	
	
	/**
	 *  갯수를 리턴한다. 해당하는 릴레이션의 
	 * @param CommitID
	 * @param SrcName
	 * @param TarName
	 * @return
	 */
	public int getCountRelation(ProjectCommit CommitID, String SrcName,String TarName)
	{
		Integer CountR = null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			 Criterion commitEq = Restrictions.eq("commit", CommitID);
			 Criterion  Tarlike = Restrictions.like("TarName", TarName+"%");
			 Criterion  Srclike = Restrictions.like("SrcName", SrcName+"%");
			 Criteria crit = session.createCriteria(NodeRelation.class);
			 crit.setProjection( Projections.rowCount() );
			 crit.add(commitEq);
			 crit.add(Tarlike);
			 crit.add(Srclike);
			 CountR = (Integer) crit.uniqueResult();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return CountR;
	}
	
	public void DataFlush()
	{
		Session session = sessionFactory.getCurrentSession();
		session.flush();
	}
}
