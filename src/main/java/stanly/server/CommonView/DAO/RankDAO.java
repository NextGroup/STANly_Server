package stanly.server.CommonView.DAO;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import stanly.server.Analysis.Model.Metric.Rate.MetricRate;

public class RankDAO {
	
	protected static final Logger logger = Logger.getLogger("RankDAO");
	/**
	 * 하이버네이트 연결을 위한 세션을 만들어주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public int getTotalARank()
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select max(metric.fatRate) from ElementNodeMetric metric where metric.fatRate!=?");
			query.setParameter(0, MetricRate.NO_RATE);
			Double ave  = (Double)query.uniqueResult();
			data = (int)Math.ceil(ave.doubleValue()); 
		}catch(Exception e)
		{
			logger.error(e);
		}
		return data;
	}
	
}
