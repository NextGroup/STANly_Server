package stanly.server.PollutionView.DAO;

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
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.PollutionView.JSON.PollutionList;

@Repository
@Transactional
public class PollutionViewDAO {
	protected static final Logger logger = Logger.getLogger("PollutionViewDAO");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	private PollutionList AddPollutionSet(PollutionList p, String Name , ProjectCommit commit)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select metric."+Name+" ,count(metric."+Name+") from ElementNodeMetric metric where metric.element.commit = ? group by metric."+Name+" order by metric."+Name+" desc");
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
						p.add(Name,"A", (int)rate);
						break;
					case MetricRate.B_RATE:
						rate = (Long)datas[1];
						p.add(Name,"B", (int)rate);
						break;
					case MetricRate.C_RATE:
						rate = (Long)datas[1];
						p.add(Name,"C", (int)rate);
						break;
					case MetricRate.F_RATE:
						rate = (Long)datas[1];
						p.add(Name,"F", (int)rate);
						break;
				}
			}

		}catch(Exception e)
		{
			logger.error(e);
		}
		return p;
	}
	public PollutionList getFatList(ProjectCommit commit)
	{
		PollutionList pList = new PollutionList();
		AddPollutionSet(pList,"UnitsRate",commit);
		AddPollutionSet(pList,"ELOCRate",commit);
		AddPollutionSet(pList,"NOMRate",commit);
		AddPollutionSet(pList,"NOFRate",commit);
		AddPollutionSet(pList,"CCRate",commit);
		return pList;
	}
	
	public PollutionList getCOList(ProjectCommit commit)
	{
		PollutionList pList = new PollutionList();
		AddPollutionSet(pList,"TangleRate",commit);
		AddPollutionSet(pList,"NoRRate",commit);
		return pList;
	}
	public PollutionList getCPList(ProjectCommit commit)
	{
		PollutionList pList = new PollutionList();
		AddPollutionSet(pList,"DITRate",commit);
		AddPollutionSet(pList,"DRate",commit);
		return pList;
	}
	
	private int getClassCount(ProjectCommit commit)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from ProjectElementNode where commit = ? and (type= ? or type = ?)");
		query.setParameter(0, commit);
		query.setParameter(1, NodeType.CLASS);
		query.setParameter(2, NodeType.INTERFACE);
		Long data = (Long)query.uniqueResult();
		return (int)(long)data;
	}
	
	private PollutionList addSARank(PollutionList p,  StaticAnalysisType type , ProjectCommit commit,int classCount)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select count(metric.NSLeft) from StaticAnalysisMetric metric where metric.type = ?and metric.commit = ? group by metric.NSLeft");
			query.setParameter(0, type);
			query.setParameter(1, commit);
			int b=0,c=0,f=0;
			List list = query.list();
			for(int i=0;i<list.size();i++)
			{
			
				long data = (Long)list.get(i);
				if(data<=5)
					b++;
				else if(data<=10)
					c++;
				else
					f++;
			}
			if(type == StaticAnalysisType.BASIC)
			{
				if(classCount-(b+c+f)!=0)
					p.add("BASIC","A", classCount-(b+c+f));	
				if(b!=0)
				p.add("BASIC", "B", b);
				if(c!=0)
				p.add("BASIC", "C", c);
				if(f!=0)
				p.add("BASIC", "F", f);
			}
			else
			{
				if(classCount-(b+c+f)!=0)
					p.add("NAMING","A", classCount-(b+c+f));	
				if(b!=0)
				p.add("NAMING", "B", b);
				if(c!=0)
				p.add("NAMING", "C", c);
				if(f!=0)
				p.add("NAMING", "F", f);
			}
		}catch(Exception e)
		{
			logger.error(e);
		}
	
		return p;
	}
	public PollutionList getSAList(ProjectCommit commit)
	{
		PollutionList pList = new PollutionList();
		int ClassCount =  getClassCount(commit);
		addSARank(pList,StaticAnalysisType.BASIC,commit,ClassCount);
		addSARank(pList,StaticAnalysisType.NAMING,commit,ClassCount);
		return pList;
	}
}
