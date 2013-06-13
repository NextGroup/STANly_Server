package stanly.server.CommonView.DAO;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.ElementNodeMetric;
import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.CommonView.JSON.CriticalRiskList;
import stanly.server.CommonView.JSON.PollutionRatioList;
import stanly.server.CommonView.JSON.SAList;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;

@Repository
@Transactional
public class RankDAO {
	
	protected static final Logger logger = Logger.getLogger("RankDAO");
	/**
	 * 하이버네이트 연결을 위한 세션을 만들어주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	private String RateChange(int i)
	{
		return (i==1) ? "A":((i==2) ? "B" : ((i==3) ? "C":"F"));
	}
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
			Query query = session.createQuery("select metric from ElementNodeMetric metric where metric.TotalRate > ? and  metric.element.commit = ? and metric.element.type !=? and metric.element.type !=? order by metric.TotalRate desc");
			query.setParameter(0, MetricRate.A_RATE);
			query.setParameter(1, commit);
			query.setParameter(2, NodeType.FIELD);
			query.setParameter(3, NodeType.METHOD);
			query.setMaxResults(5);

			 List nodeList = query.list();

			 
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
	
	public PollutionRatioList getPollutionList(ProjectCommit commit)
	{
		PollutionRatioList pList = new PollutionRatioList();
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select metric.TotalRate ,count(metric.TotalRate) from ElementNodeMetric metric where metric.element.commit = ? group by metric.TotalRate order by metric.TotalRate desc");
			query.setParameter(0, commit);
			
			List group = query.list();
			Iterator ite = group.iterator();
			long rate=0;
			while(ite.hasNext())
			{
				Object[] datas = (Object[]) ite.next();
				switch((Integer)datas[0])
				{
					case MetricRate.A_RATE:
						rate = (Long)datas[1];
						pList.insertRatio("A", (int)rate);
						break;
					case MetricRate.B_RATE:
						rate = (Long)datas[1];
						pList.insertRatio("B", (int)rate);
						break;
					case MetricRate.C_RATE:
						rate = (Long)datas[1];
						pList.insertRatio("C", (int)rate);
						break;
					case MetricRate.F_RATE:
						rate = (Long)datas[1];
						pList.insertRatio("F", (int)rate);
						break;
				}
			}

		}catch(Exception e)
		{
			logger.error(e);
		}

		return pList;
	}
	
	
	
	public PollutionRatioList getFatRank(ProjectCommit commit)
	{
		PollutionRatioList pList = new PollutionRatioList();
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select metric.fatRate ,count(metric.fatRate) from ElementNodeMetric metric where metric.element.commit = ? group by metric.fatRate");
			query.setParameter(0, commit);
			List group = query.list();
			Iterator ite = group.iterator();
			int LastI = -1;
			int LastData=0;
			int Total=0;
			while(ite.hasNext())
			{
				Object[] datas = (Object[]) ite.next();
				if((Integer)datas[0] > LastI)
				{
					LastI=(Integer)datas[0];
					long Temp = ((Long)datas[1]);
					LastData = (int)Temp;
					Total+=LastData;
				}
				else
				{
					long Temp = ((Long)datas[1]);
					Total+=(int)Temp;
				}

			}
			if(LastI==1)
				pList.insertRatio("A", Total);
			else
			{
				pList.insertRatio(RateChange(LastI),LastData);
				pList.insertRatio("etc", Total-LastData);

			}
			
		}catch(Exception e)
		{
			logger.error(e);
		}
		return pList;
	}
	public PollutionRatioList getCpRank(ProjectCommit commit)
	{
		PollutionRatioList pList = new PollutionRatioList();
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select metric.CPRate ,count(metric.CPRate) from ElementNodeMetric metric where metric.element.commit = ? group by metric.CPRate");
			query.setParameter(0, commit);
			List group = query.list();
			Iterator ite = group.iterator();
			int LastI = -1;
			int LastData=0;
			int Total=0;
			while(ite.hasNext())
			{
				Object[] datas = (Object[]) ite.next();
				if((Integer)datas[0] > LastI)
				{
					LastI=(Integer)datas[0];
					long Temp = ((Long)datas[1]);
					LastData = (int)Temp;
					Total+=LastData;
				}
				else
				{
					long Temp = ((Long)datas[1]);
					Total+=(int)Temp;
				}

			}
			if(LastI==1)
				pList.insertRatio("A", Total);
			else
			{
				pList.insertRatio(RateChange(LastI),LastData);
				pList.insertRatio("etc", Total-LastData);

			}
			
		}catch(Exception e)
		{
			logger.error(e);
		}
		return pList;
	}
	public PollutionRatioList getCouplingRank(ProjectCommit commit)
	{
		PollutionRatioList pList = new PollutionRatioList();
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select metric.CouplingRate ,count(metric.CouplingRate) from ElementNodeMetric metric where metric.element.commit = ? group by metric.CouplingRate");
			query.setParameter(0, commit);
			List group = query.list();
			Iterator ite = group.iterator();
			int LastI = -1;
			int LastData=0;
			int Total=0;
			while(ite.hasNext())
			{
				Object[] datas = (Object[]) ite.next();
				if((Integer)datas[0] > LastI)
				{
					LastI=(Integer)datas[0];
					long Temp = ((Long)datas[1]);
					LastData = (int)Temp;
					Total+=LastData;
				}
				else
				{
					long Temp = ((Long)datas[1]);
					Total+=(int)Temp;
				}

			}
			if(LastI==1)
				pList.insertRatio("A", Total);
			else
			{

				pList.insertRatio(RateChange(LastI),LastData);
				pList.insertRatio("etc", Total-LastData);
			}
			
		}catch(Exception e)
		{
			logger.error(e);
		}
		return pList;
	}
	
	public SAList getStaticAnalysisList(ProjectCommit commit)
	{
		SAList list = new SAList();
		ProjectInfo pinfo = commit.getPInfo();
		list.add("NAMING", RateChange(pinfo.getName_RANK()));
		list.add("BASIC", RateChange(pinfo.getBasic_RANK()));
		int ave = (pinfo.getBasic_RANK()+pinfo.getCoplingRANK()+pinfo.getFAT_RANK()+pinfo.getName_RANK())/4;
		list.add("PROJECT", RateChange(ave));
		return list;
	}
}
