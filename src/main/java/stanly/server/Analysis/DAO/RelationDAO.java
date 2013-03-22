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

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Relation.NodeRelation;
import stanly.server.GitProject.Model.ProjectCommit;

/**
 * @author Karuana
 * 릴레이션에 대한 정보를 DB에 접근하여 처리하는 DAO
 */
@Repository
@Transactional
public class RelationDAO {
	protected static final Logger logger = Logger.getLogger("RelationDAO");
	
	/**
	 * 하이버네이트 연결을 위한 세션을 만들어주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	/**
	 * 주어진 릴레이션 객체를 DB에 등록한다.
	 * @param relation 등록할 객
	 * @return 등록된 객체를 반환한다.
	 */
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
			 //이거 수정해야할 필요가 있음 
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
	 * Tar Realti
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
			 Criterion tarEq = Restrictions.not( Restrictions.like("TarName", SrcName+"%"));
			 Criteria crit = session.createCriteria(NodeRelation.class);
			 crit.add(commitEq);
			 crit.add(Srclike);
			 crit.add(tarEq);
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
	public long getCountRelation(ProjectCommit CommitID, String SrcName,String TarName)
	{
		Long CountR = null;
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
			 CountR = (Long) crit.uniqueResult();
	
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return CountR;
	}
	
	public long getCountRelation(ProjectCommit CommitID, String SrcName,String TarName,List<ProjectElementNode> ignore,boolean srcable)
	{
		Long CountR = null;
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
			 for(int i=0;i<ignore.size();i++)
			 {
				 if(srcable)
				 {
					 Criterion  unSrclike = Restrictions.not(Restrictions.like("SrcName", ignore.get(i).getName()+"%"));
					 crit.add(unSrclike);
				 }
				 else
				 {
					 Criterion  unTarlike = Restrictions.not(Restrictions.like("TarName", ignore.get(i).getName()+"%"));
					 crit.add(unTarlike);
				 }
				
			 }
	
			 CountR = (Long) crit.uniqueResult();
	
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return CountR;
	}
	
	public void DataFlush()
	{
		Session session = sessionFactory.getCurrentSession();
		session.flush();
	}
}
