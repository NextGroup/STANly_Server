package stanly.server.MetricView.DAO;

import java.util.ArrayList;
import java.util.Stack;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.ClassMetric;
import stanly.server.Analysis.Model.Metric.LibraryMetric;
import stanly.server.Analysis.Model.Metric.MethodMetric;
import stanly.server.Analysis.Model.Metric.PackageMetric;
import stanly.server.Analysis.Model.Metric.PackageSetMetric;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.MetricView.Json.CodeSizeValue;
import stanly.server.MetricView.Json.MartinDistanceChart;
import stanly.server.MetricView.Json.MartinMetricList;
import stanly.server.MetricView.Json.PollutionList;

@Repository
@Transactional
public class MetricSearchDAO {
	protected static final Logger logger = Logger.getLogger("MetricDAO - Search");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private String sprite(String path)
	{
		String[]  arr =  path.split("\\.");
		logger.info((arr.length>1) ?  arr[arr.length-1]:path);
		return (arr.length>1) ?  arr[arr.length-1]:path;
	}
	
	private ProjectElementNode getElementNodeByType(ProjectCommit commit, NodeType type)
	{		
		ProjectElementNode  rootNode =null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion projectEq = Restrictions.eq("NSLeft", 1); //NSLeft == 1 이면 프로젝트 노
			Criteria crit = session.createCriteria(ProjectElementNode.class);
			crit.add(CommitEq);
			crit.add(projectEq);
			rootNode = (ProjectElementNode) crit.uniqueResult();
	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
	
		return rootNode;
	}
	
	
	/**
	 * Martin 벨류를 계산하는 로직 ㅇ
	 * @param commit
	 * @param NSLeft
	 * @return
	 */
	public MartinMetricList getMertinValue(ProjectCommit commit, int NSLeft)
	{
		MartinMetricList mertin = new MartinMetricList();
		
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion projectEq = Restrictions.eq("NSLeft", NSLeft); //NSLeft == 1 이면 프로젝트 노
			Criteria crit = session.createCriteria(ProjectElementNode.class);
			crit.add(CommitEq);
			crit.add(projectEq);
			ProjectElementNode targetNode = (ProjectElementNode) crit.uniqueResult();
			NodeType type = targetNode.getType();
			
			switch(type)
			{
		
				case PACKAGE:
					PackageMetric metric  =(PackageMetric) targetNode.getEMetric();
					if(!mertin.addAbstractness(metric.getAbstractness()))
						throw new Exception("Abstractness Get Error");
					if(!mertin.addAfferentCoupling(metric.getAfferentCoupling()))
						throw new Exception("AffeterntCoupling Get Error");
					if(!mertin.addDistance(metric.getDistance()))
						throw new Exception("Distance Get Error");
					if(!mertin.addEfferentCoupling(metric.getEfferentCoupling()))
						throw new Exception("EfferentCoupling Get Error");
					if(!mertin.addInstability(metric.getInstability()))
						throw new Exception("Instability Get Error");
					break;
				default:
					throw new Exception("Type Error");
			}	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return mertin;
		}
		return mertin;
	}
	
	
	
	/**
	 * 선택한 라이브러리의 NSleft를 넘겨준다.
	 * @param commit
	 * @param NSleft
	 * @param NSRight
	 * @return
	 */
	public CodeSizeValue getCodeSize(ProjectCommit commit,int NSLeft,int NSRight)
	{
		CodeSizeValue codesize = null;
		
		try{
			ProjectInfo info = commit.getPInfo();
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion Left = Restrictions.ge("NSLeft", new Integer(NSLeft));
			Criterion Right = Restrictions.le("NSRight", new Integer(NSRight));
			Criterion NType =Restrictions.or(Restrictions.eq("type", NodeType.LIBRARY), Restrictions.eq("type", NodeType.PACKAGE));
			Criterion NType2 =Restrictions.or(Restrictions.eq("type", NodeType.PACKAGESET), Restrictions.eq("type", NodeType.CLASS));
			Criterion NType3 =Restrictions.or(Restrictions.eq("type", NodeType.ENUM), Restrictions.eq("type", NodeType.INTERFACE));
			Criterion orType = Restrictions.or(Restrictions.or(NType,NType2),NType3);
			
			Criteria crit = session.createCriteria(ProjectElementNode.class);
			crit.add(CommitEq);
			crit.add(Left);
			crit.add(Right);
			crit.add(orType);

			crit.addOrder(Order.asc("NSLeft"));
			
			ArrayList<ProjectElementNode> treeList = (ArrayList<ProjectElementNode>) crit.list();
			
			ProjectElementNode StartNode = treeList.get(0);
			
				codesize =  
						new CodeSizeValue((StartNode.getType() == NodeType.LIBRARY) ? " ":sprite(StartNode.getName()),StartNode.getNSLeft(),StartNode.getNSRight()) ;
				Stack<CodeSizeValue> depth = new Stack<CodeSizeValue>();
				depth.push(codesize);// 최상위 부모 노드를 넣는다. 
				int NStriger = 1;
				for(int i=1;i<treeList.size();i++)
				{
					ProjectElementNode node = treeList.get(i);
					
					if(node.getNSLeft()>NStriger)
					{
						//Stack에 들어잇는 애들 비우기 
						while(depth.peek().getNSRight()<node.getNSLeft())
							depth.pop();
						CodeSizeValue value = null;
						switch(node.getType())
						{
							case PACKAGESET:
							case PACKAGE:
								value = new CodeSizeValue(sprite(node.getName()),node.getNSLeft(),node.getNSRight());
								depth.peek().addChildNode(value);
								depth.push(value);
								break;
							case CLASS:
							case INTERFACE:
							case ENUM:
								ClassMetric metric = (ClassMetric) node.getEMetric();
								value = new CodeSizeValue(sprite(node.getName()),metric.getLOC(),node.getNSLeft(),node.getNSRight());
								depth.peek().addChildNode(value);
								NStriger=node.getNSRight();
								break;
						}
					}				
				}
			
				
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return codesize;
		}
		
		return codesize;
	}
	
	/**
	 * Martin 벨류를 계산하는 로직 ㅇ
	 * @param commit
	 * @param NSLeft
	 * @return
	 */
	public MartinDistanceChart getMartinDistance(ProjectCommit commit, int NSLeft,int NSRight)
	{
		MartinDistanceChart martin = new MartinDistanceChart();
		
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion Left = Restrictions.ge("NSLeft", new Integer(NSLeft));
			Criterion Right = Restrictions.le("NSRight", new Integer(NSRight));
			
			Criterion NType = Restrictions.eq("type", NodeType.PACKAGE);
			Criteria crit = session.createCriteria(ProjectElementNode.class);
			crit.add(CommitEq);
			crit.add(Left);
			crit.add(Right);
			crit.add(NType);
			
			ArrayList<ProjectElementNode> packageNodes = (ArrayList<ProjectElementNode>) crit.list();
			for(ProjectElementNode node:packageNodes)
			{
				PackageMetric metric  =(PackageMetric) node.getEMetric();				
				martin.addPackage(node.getName(), metric.getAbstractness(), metric.getInstability(), metric.getLOC());
			}
			
			//ProjectElementNode targetNode = (ProjectElementNode) crit.uniqueResult();
			//NodeType type = targetNode.getType();
			/*
			switch(type)
			{		
				case PACKAGE:
					PackageMetric metric  =(PackageMetric) targetNode.getEMetric();
					if(!mertin.addAbstractness(metric.getAbstractness()))
						throw new Exception("Abstractness Get Error");					
					if(!mertin.addDistance(metric.getDistance()))
						throw new Exception("Distance Get Error");					
					if(!mertin.addInstability(metric.getInstability()))
						throw new Exception("Instability Get Error");
					break;
				default:
					throw new Exception("Type Error");
			}	*/
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return martin;
		}
		return martin;
	}
	
	public PollutionList getPollutionList(ProjectCommit commit, int NSLeft,int NSRight)
	{
		PollutionList pollution = new PollutionList();
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion Left = Restrictions.ge("NSLeft", new Integer(NSLeft));
			Criterion Right = Restrictions.le("NSRight", new Integer(NSRight));
			Criterion NotField = Restrictions.not(Restrictions.eq("type", NodeType.FIELD));
			Criteria crit = session.createCriteria(ProjectElementNode.class);

			crit.add(CommitEq);
			crit.add(Left);
			crit.add(Right);
			crit.add(NotField);
			ArrayList<ProjectElementNode> treeList = (ArrayList<ProjectElementNode>) crit.list();
			logger.info("Tree 계산ㅇㅇ"+treeList.size());
			for(int i=0;i<treeList.size();i++)
			{
					NodeType type = treeList.get(i).getType();
					switch(type)
					{
						case LIBRARY:
							calcPollution(pollution,(LibraryMetric)treeList.get(i).getEMetric());
							break;
						case PACKAGESET:
							calcPollution(pollution,(PackageSetMetric)treeList.get(i).getEMetric());
							break;
						case PACKAGE:
							calcPollution(pollution,(PackageMetric)treeList.get(i).getEMetric());
							break;
						case CLASS:
						case ENUM:
						case INTERFACE:
						case ANNOTATION:
							calcPollution(pollution,(ClassMetric)treeList.get(i).getEMetric());
							break;
						case METHOD:
						case CONSTRUCTOR:
							calcPollution(pollution,(MethodMetric)treeList.get(i).getEMetric());
							break;
		
					}
			}

			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return pollution;
		}
		
		
		return pollution;
		
	}
	
	private PollutionList calcPollution(PollutionList pollution, LibraryMetric lib)
	{
		
		if(lib.getDistanceAbsolute() > 4)
			pollution.addPollution("Average Absolute Distance", lib.getElement().getName(), (int)lib.getDistanceAbsolute());
		if(lib.getDIT()>1)
			pollution.addPollution("Depth of Inheritance Tree", lib.getElement().getName(), (int)lib.getDIT());
		
		return pollution;
	}
	private PollutionList calcPollution(PollutionList pollution, PackageSetMetric packageset)
	{
		
		if(packageset.getFat()>60)
			pollution.addPollution("Fat", packageset.getElement().getName(), (int)packageset.getFat());
		if(packageset.getTangled()>1)
			pollution.addPollution("Tangled", packageset.getElement().getName(), (int)packageset.getTangled());
		
		return pollution;
	}
	private PollutionList calcPollution(PollutionList pollution, PackageMetric packageM)
	{
		
		if(packageM.getFat()>60)
			pollution.addPollution("Tangled", packageM.getElement().getName(), (int)packageM.getFat());
		if(packageM.getUnits()>40)
			pollution.addPollution("Number of Top Level Classes", packageM.getElement().getName(), (int)packageM.getUnits());
		if(packageM.getDistance()>0.5f || packageM.getDistance()<-0.5f )
			pollution.addPollution("Distance (D)", packageM.getElement().getName(), (int)packageM.getDistance());
		
		return pollution;
	}
	private PollutionList calcPollution(PollutionList pollution, ClassMetric classM)
	{
		
		if(classM.getMethods()>50)
			pollution.addPollution("Number of Methods", classM.getElement().getName(), (int)classM.getMethods());
		if(classM.getFields()>20)
			pollution.addPollution("Number of Fields", classM.getElement().getName(), (int)classM.getFields());
		if(classM.getLOC()>300)
			pollution.addPollution("Estimated Lines of Code (ELOC)", classM.getElement().getName(), (int)classM.getLOC());
		if(classM.getFat()>60)
			pollution.addPollution("Fat", classM.getElement().getName(), (int)classM.getFat());
		if(classM.getWMC()>100)
			pollution.addPollution("Weighted Methods per Class", classM.getElement().getName(), (int)classM.getWMC());
		if(classM.getDIT()>6)
			pollution.addPollution("Depth of Inheritance Tree", classM.getElement().getName(), (int)classM.getDIT());
		if(classM.getCBO()>100)
			pollution.addPollution("Coupling between Objects ", classM.getElement().getName(), (int)classM.getCBO());
		if(classM.getRFC()>100)
			pollution.addPollution("Response for a Class ", classM.getElement().getName(), (int)classM.getRFC());
		
		
		return pollution;
	}
	private PollutionList calcPollution(PollutionList pollution, MethodMetric MethodM)
	{
		

		if(MethodM.getLOC()>60)
			pollution.addPollution("Estimated Lines of Code (ELOC)", MethodM.getElement().getName(), (int)MethodM.getLOC());
		if(MethodM.getCC()>15)
			pollution.addPollution("Cyclomatic Complexity", MethodM.getElement().getName(), (int)MethodM.getCC());
		
		
		
		return pollution;
	}
}
