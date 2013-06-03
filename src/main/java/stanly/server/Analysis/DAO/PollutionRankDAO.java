package stanly.server.Analysis.DAO;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.Analysis.Model.StaticAnalysis.Type.StaticAnalysisType;

@Repository
@Transactional
public class PollutionRankDAO {
	protected static final Logger logger = Logger.getLogger("RelationDAO");
	/**
	 * 하이버네이트 연결을 위한 세션을 만들어주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private int getStaticAnalysisRank(int count)
	{
		return (count<11) ? MetricRate.A_RATE:((count<51) ? MetricRate.B_RATE: ((count<101)? MetricRate.C_RATE: MetricRate.F_RATE)); 
	}
	
	public int getProjectFATRate()
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select avg(metric.fatRate) from ElementNodeMetric metric where metric.fatRate!=?");
			query.setParameter(0, MetricRate.NO_RATE);
			Double ave  = (Double)query.uniqueResult();
			data = (int)ave.doubleValue(); 
		}catch(Exception e)
		{
			logger.error(e);
		}
		return data;
	}
	
	public int getProjectCPRate()
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select avg(metric.CPRate) from ElementNodeMetric metric where metric.CPRate!=?");
			query.setParameter(0, MetricRate.NO_RATE);
			Double ave  = (Double)query.uniqueResult();
			data = (int)ave.doubleValue(); 
		}catch(Exception e)
		{
			logger.error(e);
		}
		return (data);
	}
	
	public int getProjectCouplingRate()
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select avg(metric.CouplingRate) from ElementNodeMetric metric where metric.CouplingRate!=?");
			query.setParameter(0, MetricRate.NO_RATE);
			Double ave  = (Double)query.uniqueResult();
			data = (int)ave.doubleValue(); 
		}catch(Exception e)
		{
			logger.error(e);
		}
		return (data);
	}
	
	public int getBasicStaticAnalysisRank()
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select count(*) from StaticAnalysisMetric metric where metric.type = ? ");
			query.setParameter(0, StaticAnalysisType.BASIC);
			Long count  = (Long)query.uniqueResult();
			 data= count.intValue();
		}catch(Exception e)
		{
			logger.error(e);
		}
		return getStaticAnalysisRank(data);
	}
	
	public int getNameStaticAnalysisRank()
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select count(*) from StaticAnalysisMetric metric where metric.type = ? ");
			query.setParameter(0, StaticAnalysisType.NAMING);
			Long count  = (Long)query.uniqueResult();
			 data=count.intValue();
		}catch(Exception e)
		{
			logger.error(e);
		}
		return getStaticAnalysisRank(data);
	}
}
