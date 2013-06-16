package stanly.server.PollutionView.DAO;

import java.util.ArrayList;
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
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.PollutionView.JSON.PollutionList;
import stanly.server.PollutionView.JSON.SelectedRisk;
import stanly.server.PollutionView.JSON.SelectedRiskList;

@Repository
@Transactional
public class PollutionViewDAO {
	protected static final Logger logger = Logger.getLogger("PollutionViewDAO");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private String getStaticAnalysisRank(int Count)
	{
		return  (Count == 0) ? "A": (Count<=5) ? "B": ((Count<=10) ? "C":"F");
	}

	
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
						p.add(Name,0, (int)rate);
						break;
					case MetricRate.B_RATE:
						rate = (Long)datas[1];
						p.add(Name,1, (int)rate);
						break;
					case MetricRate.C_RATE:
						rate = (Long)datas[1];
						p.add(Name,2, (int)rate);
						break;
					case MetricRate.F_RATE:
						rate = (Long)datas[1];
						p.add(Name,3, (int)rate);
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
				p.add("BASIC",0, classCount-(b+c+f));	
				p.add("BASIC", 1, b);
				p.add("BASIC", 2, c);
				p.add("BASIC", 3, f);
			}
			else
			{			
				p.add("NAMING",0, classCount-(b+c+f));	
				p.add("NAMING", 1, b);
				p.add("NAMING", 2, c);
				p.add("NAMING", 3, f);
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
	
	private SelectedRisk setDomain(NodeType type, SelectedRisk risk)
	{
		switch(type)
		{
			case CLASS: 
				risk.setDomain("CLASS");
				break;
			case INTERFACE:
				risk.setDomain("INTERFACE");
				break;
			case PROJECT: 
				risk.setDomain("PROJECT");
				break;
			case LIBRARY:
				risk.setDomain("LIBRARY");
				break;
			case PACKAGE: 
				risk.setDomain("PACKAGE");
				break;
			case PACKAGESET:
				risk.setDomain("PACKAGESET");
				break;
			case FIELD:
				risk.setDomain("FIELD");
				break;
			case METHOD:
				risk.setDomain("METHOD");
				break;
			case ENUM:
				risk.setDomain("ENUM");
				break;
			case CONSTRUCTOR:
				risk.setDomain("CONSTRUCTOR");
				break;
			case ANNOTATION:
				risk.setDomain("ANNOTATION");
				break;
		}	
		return risk;
	}
	
	private HashMap<Integer, SelectedRisk> updateElementNode(HashMap<Integer, SelectedRisk> map, ProjectCommit commit)
	{
		try{
			Session session = sessionFactory.getCurrentSession();
				if(!map.isEmpty())
				{
					Query query = session.createQuery("select node.NSLeft, node.type, node.Name, node.SingleName from ProjectElementNode node where node.commit = ? and node.NSLeft in ( :left )");
		
				query.setParameter(0, commit);
				query.setParameterList("left", map.keySet());
				
				List data = query.list();
				Iterator ite = data.iterator();
				HashMap<String,Integer> singleMap = new HashMap<String,Integer>();
				
				while(ite.hasNext())
				{
					Object[] obj = (Object[])ite.next();
					int key = (Integer)obj[0];
					NodeType type = (NodeType)obj[1];
					String name = (String)obj[2];
					singleMap.put((String)obj[3],key);
					
					SelectedRisk risk = map.get(key);
					risk.setDomainName(name);
					setDomain(type, risk);
				}
				// 두번째 관련 개발자 찾기 
				
				Query query2 = session.createQuery("select CI.influenceClass, count(CI.influenceClass) from CommitterInfluence CI group by CI.influenceClass having CI.influenceClass in (:name)");
					
				query2.setParameterList("name", singleMap.keySet());
				data = query2.list();
				ite = data.iterator();
				while(ite.hasNext())
				{
					Object[] obj = (Object[])ite.next();
					String key = (String)obj[0];
					SelectedRisk risk = map.get(singleMap.get(key));
					long linked = (Long) obj[1];
					risk.setLinkedPerson((int)linked);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}
	
	
	private SelectedRiskList createSARisk(ProjectCommit commit, StaticAnalysisType type, int Rank)
	{
		SelectedRiskList riskList = new SelectedRiskList();
		try{
				HashMap<Integer, SelectedRisk> map = new HashMap<Integer, SelectedRisk>();
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createQuery("select metric.NSLeft, count(metric.NSLeft) from StaticAnalysisMetric metric where metric.type = ? and metric.commit = ? group by metric.NSLeft");
				query.setParameter(0, type);
				query.setParameter(1, commit);
				List data = query.list();
				Iterator ite = data.iterator();
				
				//(Count == 0) ? "A": (Count<=5) ? "B":((Count<=10) ? "C":"F");
				while(ite.hasNext())
				{
					Object[] obj = (Object[])ite.next();
					int key = (Integer)obj[0];
					long count = (Long)obj[1];
				
					if(Rank==0) //B전체 
						map.put(key, new SelectedRisk(key,getStaticAnalysisRank((int)count)));
					else if (Rank==1)// B 등급 
					{	
						if(count>5) 
							map.put(key, new SelectedRisk(key,getStaticAnalysisRank((int)count)));
					}
					else if(Rank==2)// C등급 이하
					{
						if(count>10) 
							map.put(key, new SelectedRisk(key,getStaticAnalysisRank((int)count)));
					}	
					

				}
				updateElementNode(map,commit);	
				Iterator it = map.values().iterator();

				while(it.hasNext())
				{
					SelectedRisk obj = (SelectedRisk)it.next();
					obj.setType((type==StaticAnalysisType.BASIC) ? "BASIC":"NAMING");
					obj.setRiskName((type==StaticAnalysisType.BASIC) ? "BASIC":"NAMING");
					riskList.add(obj);
				}
				
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return riskList;
	}

	private String ConvertColumCode(int i)
	{
		String Data;
		switch(i)
		{
		case 0:
			Data="UnitsRate";
			break;
		case 1:
			Data="ELOCRate";
			break;
		case 2:
			Data="NOMRate";
			break;
		case 3:
			Data="NOFRate";
			break;
		case 4:
			Data="CCRate";
			break;
		case 5:
			Data="DITRate";
			break;
		case 6:
			Data="DRate";
			break;
		case 7:
			Data="NoRRate";
			break;
		case 8:
			Data="TangleRate";
			break;
			default:
				Data="TangleRate";
		}
		
		return Data;
	}
	
	private String ConvertTypeCode(int i)
	{
		return (i>=0 && i<=4) ? "FAT": ((i>=5 && i<=6) ? "Change Propagation":"Coupling");
	}
	private String ConvertRiskName(int i)
	{
		String Data;
		switch(i)
		{
		case 0:
			Data="Units";
			break;
		case 1:
			Data="ELOC";
			break;
		case 2:
			Data="Number of methods";
			break;
		case 3:
			Data="Number of field";
			break;
		case 4:
			Data="Number of Branch statement";
			break;
		case 5:
			Data="DIT";
			break;
		case 6:
			Data="Balancing Abstractness";
			break;
		case 7:
			Data="Number of Relationship";
			break;
		case 8:
			Data="Tangle";
			break;
			default:
				Data="Tangle";
		}
		
		return Data;
	}
	private SelectedRiskList createPollutionList(ProjectCommit commit, int dataType, int Rank)
	{
		SelectedRiskList riskList = new SelectedRiskList();
		try{
				
				String Type = ConvertColumCode(dataType);
				HashMap<Integer, SelectedRisk> map = new HashMap<Integer, SelectedRisk>();
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createQuery("select metric.element.NSLeft , metric."+Type+" from ElementNodeMetric metric where metric."+Type+" != ? and metric."+Type+" > ? and metric.element.commit = ? ");
				query.setParameter(0, MetricRate.NO_RATE);
				query.setParameter(1, Rank);
				query.setParameter(2, commit);
				List data = query.list();
				Iterator ite = data.iterator();
				while(ite.hasNext())
				{
					Object[] obj = (Object[])ite.next();
					int key = (Integer)obj[0];
					long count = (Integer)obj[1];
				
			
						map.put(key, new SelectedRisk(key,MetricRate.ChangeRate((int)count)));
				}
				updateElementNode(map,commit);	
				Iterator it = map.values().iterator();

				while(it.hasNext())
				{
					SelectedRisk obj = (SelectedRisk)it.next();
					obj.setType(ConvertTypeCode(dataType));
					obj.setRiskName(ConvertRiskName(dataType));
					riskList.add(obj);
				}
				
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return riskList;
	}
	
	
	public SelectedRiskList getSATotalList(ProjectCommit commit,StaticAnalysisType type, int Rank)
	{
		return 	createSARisk(commit,type,Rank);
	}
	
	public SelectedRiskList getPollutionRisk(ProjectCommit commit, int index,int Rank)
	{
		return createPollutionList(commit,index,Rank);
	}
}
