package stanly.server.DiffDevloper.DAO;

import java.util.HashMap;
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
import stanly.server.DiffDevloper.JSON.DiffDevList;
import stanly.server.DiffDevloper.JSON.DiffDevloperInfo;
import stanly.server.DiffDevloper.JSON.RateData;
import stanly.server.GitProject.Model.ProjectCommit;

@Repository
@Transactional
public class DiffDevDAO {
	protected static final Logger logger = Logger.getLogger("DiffDevDAO");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private int getStaticAnalysisRank(int count)
	{
		return (count<51) ? MetricRate.A_RATE:((count<101) ? MetricRate.B_RATE: ((count<201)? MetricRate.C_RATE: MetricRate.F_RATE)); 
	}
	
	private int getStaticAnalysisNamingRank(int count)
	{
		return (count<151) ? MetricRate.A_RATE:((count<551) ? MetricRate.B_RATE: ((count<1001)? MetricRate.C_RATE: MetricRate.F_RATE)); 
	}
	
	private int getBasicStaticAnalysisRank(ProjectCommit commit)
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
	
	private int getNameStaticAnalysisRank(ProjectCommit commit)
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
	
	private void updateCommitCount(HashMap<String,RateData> map,ProjectCommit commit)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select pc.Author, count(pc.Author) from ProjectCommit pc where pc.Author in (:name) and pc.PInfo = :project group by pc.Author");
			query.setParameterList("name", map.keySet());
			query.setParameter("project", commit.getPInfo());
			List list = query.list();
			Iterator it = list.iterator();
			while(it.hasNext())
			{
				Object[] obj = (Object[]) it.next();
				long count = (Long)obj[1];
				map.get(obj[0]).setCommit((int)count);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public DiffDevList getDiffDevList(ProjectCommit commit)
	{
		DiffDevList diff = new DiffDevList();
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select CI.committer, EM.EMetric.fatRate, EM.EMetric.CPRate,EM.EMetric.CouplingRate, EM.NSLeft from CommitterInfluence CI, ProjectElementNode EM where CI.influenceClass = EM.SingleName and (CI.PInfo = ? and EM.commit = ?)");
			query.setParameter(0, commit.getPInfo());
			query.setParameter(1, commit);
			List list = query.list();
			Iterator it = list.iterator();
			HashMap<String,RateData> map = new HashMap<String,RateData>();
			while(it.hasNext())
			{
				Object[] obj = (Object[]) it.next();
				if(!map.containsKey(obj[0]))
					map.put((String)obj[0], new RateData());
				RateData rm = map.get(obj[0]);
				
				int rank = (Integer)obj[1];
				rm.addFat(rank);
				rank = (Integer)obj[2];
				rm.addCprate(rank);
				rank = (Integer)obj[3];
				rm.addCorate(rank);
			

			}
			//DiffDevList에 값 삽입 
			updateCommitCount(map,commit);
			it = map.keySet().iterator();
			int basic = getBasicStaticAnalysisRank(commit);
			int naming = getNameStaticAnalysisRank(commit);
			while(it.hasNext())
			{
				String name = (String)it.next();
				RateData rate = (RateData)map.get(name);
				DiffDevloperInfo dev = new DiffDevloperInfo();
				dev.setDevloper(name);
				dev.setFatRank(MetricRate.ChangeRate(rate.getFat()));
				dev.setCouplingRank(MetricRate.ChangeRate(rate.getCorate()));
				dev.setCPRank(MetricRate.ChangeRate(rate.getCprate()));
				dev.setCommit(rate.getCommit());
				dev.setBasicRank(MetricRate.ChangeRate(basic));
				dev.setNamingRank(MetricRate.ChangeRate(naming));
				int sum = rate.getFat() + rate.getCorate() + rate.getCprate() + basic + naming;
				dev.setTotalScore(MetricRate.ChangeRate(Math.round(sum/5.0f)));
				diff.add(dev);
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return diff;
	}
}
