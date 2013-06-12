package stanly.server.Analysis.DAO;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.Analysis.Model.StaticAnalysis.Type.StaticAnalysisType;
import stanly.server.GitProject.Model.ProjectCommit;

@Repository
@Transactional
public class PollutionRankDAO {
	protected static final Logger logger = Logger.getLogger("PollutionRankDAO");
	/**
	 * 하이버네이트 연결을 위한 세션을 만들어주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private int getStaticAnalysisRank(int count)
	{
		return (count<51) ? MetricRate.A_RATE:((count<101) ? MetricRate.B_RATE: ((count<201)? MetricRate.C_RATE: MetricRate.F_RATE)); 
	}
	
	private int getStaticAnalysisNamingRank(int count)
	{
		return (count<101) ? MetricRate.A_RATE:((count<251) ? MetricRate.B_RATE: ((count<501)? MetricRate.C_RATE: MetricRate.F_RATE)); 
	}
	
	private int getRate(double a)
	{
		return (0.01>a) ?  MetricRate.A_RATE: ((0.02>a) ? MetricRate.B_RATE: ((0.04>a) ? MetricRate.C_RATE: MetricRate.F_RATE));
	}
	
	public int getProjectFATRate(ProjectCommit commit)
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select metric.fatRate ,count(metric.fatRate) from ElementNodeMetric metric where metric.fatRate!=? and metric.element.commit = ? group by metric.fatRate");
			query.setParameter(0, MetricRate.NO_RATE);
			query.setParameter(1, commit);
			List group = query.list();
			Iterator ite = group.iterator();
			long arate=0;
			long etcrate=0;
			while(ite.hasNext())
			{
				Object[] datas = (Object[]) ite.next();
				if((Integer)datas[0]==1)
				{
					
					arate= ((Long)datas[1]);
				
				}
				else
					etcrate += (Long)datas[1];
				
			}
			double rate = ((double)etcrate) / (arate+etcrate);
			logger.info("FAT"+rate);
			data = getRate(rate);
		}catch(Exception e)
		{
			logger.error("M-Fat");
			logger.error(e);
		}
		return data;
	}
	
	public int getProjectCPRate(ProjectCommit commit)
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select metric.CPRate ,count(metric.CPRate) from ElementNodeMetric metric where metric.CPRate!=? and metric.element.commit = ? group by metric.CPRate");
			query.setParameter(0, MetricRate.NO_RATE);
			query.setParameter(1, commit);
			List group = query.list();
			Iterator ite = group.iterator();
			long arate=0;
			long etcrate=0;
			while(ite.hasNext())
			{
				Object[] datas = (Object[]) ite.next();
				if((Integer)datas[0]==1)
				{
					arate= (Long)datas[1];
				
				}
				else
					etcrate += (Long)datas[1];
				
			}
			double rate = ((double)etcrate) / (arate+etcrate);
			logger.info("체"+rate);
			data = getRate(rate);
		}catch(Exception e)
		{
			logger.error("M-CPRate");
			logger.error(e);
		}
		return (data);
	}
	
	public int getProjectCouplingRate(ProjectCommit commit)
	{
		int data = MetricRate.NO_RATE;
		try{
			//pid 추가 할 것
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select metric.CouplingRate ,count(metric.CouplingRate) from ElementNodeMetric metric where metric.CouplingRate!=? and metric.element.commit = ? group by metric.CouplingRate");
			query.setParameter(0, MetricRate.NO_RATE);
			query.setParameter(1, commit);
			List group = query.list();
			Iterator ite = group.iterator();
			long arate=0;
			long etcrate=0;
			while(ite.hasNext())
			{
				Object[] datas = (Object[]) ite.next();
				if((Integer)datas[0]==1)
				{
					arate= (Long)datas[1];
				
				}
				else
					etcrate += (Long)datas[1];
				
			}
			double rate = ((double)etcrate) / (arate+etcrate);
			logger.info("커플링"+rate);
			data = getRate(rate);
		}catch(Exception e)
		{
			logger.error("M-Coupling");
			logger.error(e);
		}
		return (data);
	}
	
	public int getBasicStaticAnalysisRank(ProjectCommit commit)
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select count(*) from StaticAnalysisMetric metric where metric.type = ? and metric.commit = ?");
			query.setParameter(0, StaticAnalysisType.BASIC);
			query.setParameter(1, commit);
			Long count  = (Long)query.uniqueResult();
			 data= count.intValue();
		}catch(Exception e)
		{
			logger.error("SA-basic");
			logger.error(e);
		}
		return getStaticAnalysisRank(data);
	}
	
	public int getNameStaticAnalysisRank(ProjectCommit commit)
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select count(*) from StaticAnalysisMetric metric where metric.type = ? and metric.commit = ?");
			query.setParameter(0, StaticAnalysisType.NAMING);
			query.setParameter(1, commit);
			Long count  = (Long)query.uniqueResult();
			 data=count.intValue();
		}catch(Exception e)
		{
			logger.error("SA-Name");
			logger.error(e);
		}
		return getStaticAnalysisNamingRank(data);
	}
}
