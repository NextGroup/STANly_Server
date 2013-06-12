package stanly.server.CommonView.DAO;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.ElementNodeMetric;
import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.CommonView.JSON.CriticalRisk;
import stanly.server.CommonView.JSON.CriticalRiskList;
import stanly.server.GitProject.Model.ProjectCommit;

public class RankDAO {
	
	protected static final Logger logger = Logger.getLogger("RankDAO");
	/**
	 * 하이버네이트 연결을 위한 세션을 만들어주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private int getRate(double a)
	{
		return (0.01>a) ?  MetricRate.A_RATE: ((0.02>a) ? MetricRate.B_RATE: ((0.04>a) ? MetricRate.C_RATE: MetricRate.F_RATE));
	}
	
	public int getTotalARank(ProjectCommit commit)
	{
		int data = MetricRate.NO_RATE;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select metric.TotalRate ,count(metric.TotalRate) from ElementNodeMetric metric where metric.element.commit = ? group by metric.TotalRate");
			query.setParameter(0, commit);
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
			logger.info("Total"+rate);
			data = getRate(rate);
		}catch(Exception e)
		{
			logger.error(e);
		}
		return data;
	}
	
	public CriticalRiskList getCriticalRiskList(ProjectCommit commit)
	{
		
		 CriticalRiskList list = new CriticalRiskList();
		try{
			Session session = sessionFactory.getCurrentSession();

			
			 Criterion CommitEq = Restrictions.eq("commit", commit);
			 Criterion rate = Restrictions.ge("TotalRate", MetricRate.A_RATE); //NSLeft == 1 이면 프로젝트 노
			 Criteria crit = session.createCriteria(ElementNodeMetric.class);
			 crit.add(rate);
			 crit.add(CommitEq);
			 crit.setMaxResults(3);
			 crit.addOrder(Order.desc("TotalRate"));
			 List nodeList = crit.list();

			 
			for(int i=0;i<nodeList.size();i++)
			{
				ElementNodeMetric em = (ElementNodeMetric)nodeList.get(i);
				ProjectElementNode node = em.getElement();
				list.addData(node.getName(),em.getTotalRate(),node.getType());
			}
				
		}catch(Exception e)
		{
			logger.error(e);
		}
		return list;
	}
}
