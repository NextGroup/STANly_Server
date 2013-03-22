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
import stanly.server.Analysis.Model.Metric.ProjectMetric;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.MetricView.Json.CodeSizeValue;
import stanly.server.MetricView.Json.MartinDistanceChart;
import stanly.server.MetricView.Json.MartinMetricList;
import stanly.server.MetricView.Json.Metrics;
import stanly.server.MetricView.Json.PollutionChart;
import stanly.server.MetricView.Json.PollutionList;

/**
 * @author Karuana
 *	메트릭 검색을 위한 DAO 객체이다. 
 */
@Repository
@Transactional
public class MetricSearchDAO {
	protected static final Logger logger = Logger.getLogger("MetricDAO - Search");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	/**
	 * 노드의 이름에서 가장 마지막 데이터를 가져오기 위한 함수 
	 * @param path
	 * @return
	 */
	private String sprite(String path)
	{
		String[]  arr =  path.split("\\.");
		logger.info((arr.length>1) ?  arr[arr.length-1]:path);
		return (arr.length>1) ?  arr[arr.length-1]:path;
	}
	
	/**
	 * 해당하는 Type의 노드를 전부 가져온다.
	 * @param commit
	 * @param type
	 * @return
	 */
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
	 * Martin 벨류를 계산하여 가져온다. 
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
	 * codeSize View 를 위한 객체를 리턴한다. 
	 * 선택한 라이브러리의 NSleft를 넘겨주어야 한다 .
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
	 * Martin Chart를 그리기 위한 객체를 리턴한다.
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
				martin.addPackage(node.getName(), metric.getAbstractness(), metric.getInstability(),metric.getDistance(), metric.getLOC());
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
	
	/**
	 * 입력에 해당하는 노드의 매트릭 정보를 가져온다.
	 * @param commit
	 * @param NSLeft
	 * @return
	 */
	public Metrics getMetrics(ProjectCommit commit, int NSLeft)
	{
		Metrics val = new Metrics();
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
			
			//노드의 타입에 해당하게 Metric 정보를 초기화한다. 
			switch(type)
			{
		
				case PROJECT:
					ProjectMetric projectMetric = (ProjectMetric) targetNode.getEMetric();
					val.addValue("Libraries", projectMetric.getLibraries());
					val.addValue("Fat - Library", projectMetric.getFat());
					val.addValue("Tangled - Library", projectMetric.getTangled());
					val.addValue("ACD - Libraries", projectMetric.getACDLibrary());
					break;
				case LIBRARY:
					LibraryMetric libraryMetric = (LibraryMetric) targetNode.getEMetric();
					val.addValue("Packages", libraryMetric.getPackages());
					val.addValue("Classes / Package", libraryMetric.getUnitPerPackage());
					val.addValue("Fat - Packages", libraryMetric.getFatPackages());
					val.addValue("Fat - Unit", libraryMetric.getFatUnits());
					val.addValue("Tangled", libraryMetric.getTangled());
					val.addValue("ACD - Packages", libraryMetric.getACDPackage());
					val.addValue("ACD - Unit", libraryMetric.getACDUnit());
					val.addValue("Distance", libraryMetric.getDistance());
					val.addValue("|Distance|", libraryMetric.getDistanceAbsolute());
					val.addValue("Average WMC",libraryMetric.getWMC());
					val.addValue("Average DIT",libraryMetric.getDIT());
					val.addValue("Average NOC",libraryMetric.getNOC());
					val.addValue("Average CBO",libraryMetric.getAverageCBO());
					val.addValue("Average RFC",libraryMetric.getAverageRFC());
					val.addValue("Average LCOM",libraryMetric.getAverageLCOM());
					break;
				case PACKAGESET:
					PackageSetMetric packageSetMetric = (PackageSetMetric) targetNode.getEMetric();
					val.addValue("Units", packageSetMetric.getTotalUnit());
					val.addValue("Classes / Class", packageSetMetric.getClassesPerClass());
					val.addValue("Methods / Class", packageSetMetric.getMethodsPerClass());
					val.addValue("Fields / Class", packageSetMetric.getFieldsPerClass());
					val.addValue("ELOC", packageSetMetric.getTotalELOC());
					val.addValue("ELOC / Unit", packageSetMetric.getAverageELOC());
					val.addValue("Average CC", packageSetMetric.getAverageCC());
					val.addValue("Fat", packageSetMetric.getFat());
					val.addValue("Tangled", packageSetMetric.getTangled());
					break;
				case PACKAGE:
					PackageMetric packageMetric = (PackageMetric) targetNode.getEMetric();
					val.addValue("Units", packageMetric.getUnits());
					val.addValue("Classes / Class", packageMetric.getClassesPerClass());
					val.addValue("Methods / Class", packageMetric.getMethodsPerClass());
					val.addValue("Fields / Class", packageMetric.getFieldsPerClass());
					val.addValue("ELOC", packageMetric.getLOC());
					val.addValue("ELOC / Unit", packageMetric.getELOCPerUnit());
					val.addValue("Average CC", packageMetric.getAverageCC());
					val.addValue("Fat", packageMetric.getFat());
					val.addValue("ACD - Unit", packageMetric.getACDPerUnit());
					
					val.addValue("Afferent Coupling", packageMetric.getAfferentCoupling());
					val.addValue("Efferent Coupling", packageMetric.getEfferentCoupling());
					val.addValue("Abstractness", packageMetric.getAbstractness());
					val.addValue("Instability", packageMetric.getInstability());
					val.addValue("Distance", packageMetric.getDistance());
					
					val.addValue("Average WMC",packageMetric.getWMC());
					val.addValue("Average DIT",packageMetric.getDIT());
					val.addValue("Average NOC",packageMetric.getNOC());
					val.addValue("Average CBO",packageMetric.getAverageCBO());
					val.addValue("Average RFC",packageMetric.getAverageRFC());
					val.addValue("Average LCOM",packageMetric.getAverageLCOM());
					
					break;
				case CLASS:
				case INTERFACE:
					ClassMetric classMetric = (ClassMetric) targetNode.getEMetric();
					val.addValue("Classes", classMetric.getClasses());
					val.addValue("Methods", classMetric.getMethods());
					val.addValue("Fields", classMetric.getFields());
					val.addValue("ELOC", classMetric.getLOC());
					val.addValue("Fat", classMetric.getFat());
					
					val.addValue("Afferent Coupling", classMetric.getAfferentCoupling());
					val.addValue("Efferent Coupling", classMetric.getEfferentCoupling());
					
					val.addValue("WMC",classMetric.getWMC());
					val.addValue("DIT",classMetric.getDIT());
					val.addValue("NOC",classMetric.getNOC());
					val.addValue("CBO",classMetric.getCBO());
					val.addValue("RFC",classMetric.getRFC());
					val.addValue("LCOM",classMetric.getLCOM());
					
					break;
				case CONSTRUCTOR:
				case METHOD:
					MethodMetric methodMetric = (MethodMetric) targetNode.getEMetric();
					val.addValue("ELOC",methodMetric.getLOC());
					val.addValue("CC", methodMetric.getCC());
					break;
					//val.addValue(name, e)
					/*if(!mertin.addAbstractness(metric.getAbstractness()))
						throw new Exception("Abstractness Get Error");
					if(!mertin.addAfferentCoupling(metric.getAfferentCoupling()))
						throw new Exception("AffeterntCoupling Get Error");
					if(!mertin.addDistance(metric.getDistance()))
						throw new Exception("Distance Get Error");
					if(!mertin.addEfferentCoupling(metric.getEfferentCoupling()))
						throw new Exception("EfferentCoupling Get Error");
					if(!mertin.addInstability(metric.getInstability()))
						throw new Exception("Instability Get Error");*/
				default:
					throw new Exception("Type Error");
			}	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return val;
		}
		return val;
	}
	
	/**
	 * 오염도 리스트를 그리기 위한 객체를 리턴한다. 
	 * @param commit
	 * @param NSLeft
	 * @param NSRight
	 * @return
	 */
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
	
	/**
	 * 라이브러리 매트릭을 계산한다. 
	 * @param pollution
	 * @param lib
	 * @return
	 */
	private PollutionList calcPollution(PollutionList pollution, LibraryMetric lib)
	{
		
		if(lib.getDistanceAbsolute() > 0.4)
			pollution.addPollution("Average Absolute Distance", lib.getElement().getName(), lib.getDistanceAbsolute(),(lib.getDistanceAbsolute()>0.5) ? 1:0);
		if(lib.getDIT()<1)
			pollution.addPollution("Depth of Inheritance Tree", lib.getElement().getName(), lib.getDIT(),0);
		if(lib.getACDPackage()>0.5)
			pollution.addPollution("Average Component Dependency between Packages", lib.getElement().getName(), lib.getACDPackage(),(lib.getACDPackage()>1) ? 1:0);
		return pollution;
	}
	/**
	 * 패키지 셋에 해당하는 메트릭 정보를 체크한다. 
	 * @param pollution
	 * @param packageset
	 * @return
	 */
	private PollutionList calcPollution(PollutionList pollution, PackageSetMetric packageset)
	{
		
		
		if(packageset.getFat()>60)
			pollution.addPollution("Fat", packageset.getElement().getName(), packageset.getFat(),(packageset.getFat()>120)? 1:0);
		if(packageset.getTangled()>0)
			pollution.addPollution("Tangled", packageset.getElement().getName(), packageset.getTangled(),1);
		
		return pollution;
	}
	/**
	 * 패키지에 해당하는 메트릭 정보를 체크한다. 
	 * @param pollution
	 * @param packageM
	 * @return
	 */
	private PollutionList calcPollution(PollutionList pollution, PackageMetric packageM)
	{
		
		if(packageM.getFat()>60)
			pollution.addPollution("Fat", packageM.getElement().getName(), packageM.getFat(),(packageM.getFat()>120) ? 1:0);
		if(packageM.getUnits()>40)
			pollution.addPollution("Number of Top Level Classes", packageM.getElement().getName(), packageM.getUnits(),(packageM.getUnits()>60)? 1:0);
		if(packageM.getDistance()>0.5f || packageM.getDistance()<-0.5f )
			pollution.addPollution("Distance (D)", packageM.getElement().getName(), packageM.getDistance(),0);
		
		return pollution;
	}
	
	/**
	 * 클래스에 해당하는 메트릭 정보들을 체크한다. 
	 * @param pollution
	 * @param classM
	 * @return
	 */
	private PollutionList calcPollution(PollutionList pollution, ClassMetric classM)
	{
		
		if(classM.getMethods()>50)
			pollution.addPollution("Number of Methods", classM.getElement().getName(), classM.getMethods(),(classM.getMethods()>100) ? 1:0);
		if(classM.getFields()>20)
			pollution.addPollution("Number of Fields", classM.getElement().getName(), classM.getFields(),(classM.getFields()>40)? 1:0);
		if(classM.getLOC()>300)
			pollution.addPollution("Estimated Lines of Code (ELOC)", classM.getElement().getName(), classM.getLOC(),(classM.getLOC()>400)? 1:0);
		if(classM.getFat()>60)
			pollution.addPollution("Fat", classM.getElement().getName(), classM.getFat(),(classM.getFat()>120)?1:0);
		if(classM.getWMC()>100)
			pollution.addPollution("Weighted Methods per Class", classM.getElement().getName(), classM.getWMC(),(classM.getWMC()>200)?1:0);
		if(classM.getDIT()>6)
			pollution.addPollution("Depth of Inheritance Tree", classM.getElement().getName(), classM.getDIT(),(classM.getDIT()>8)?1:0);
		if(classM.getCBO()>100)
			pollution.addPollution("Coupling between Objects", classM.getElement().getName(), classM.getCBO(),(classM.getCBO()>250)?1:0);
		if(classM.getRFC()>100)
			pollution.addPollution("Response for a Class", classM.getElement().getName(), classM.getRFC(),(classM.getRFC()>1000)?1:0);
		
		
		return pollution;
	}
	
	/**
	 * 메소드에 해당하는 메트릭 정보들을 체크한다. 
	 * @param pollution
	 * @param MethodM
	 * @return
	 */
	private PollutionList calcPollution(PollutionList pollution, MethodMetric MethodM)
	{

		if(MethodM.getLOC()>60)
			pollution.addPollution("Estimated Lines of Code (ELOC)", MethodM.getElement().getName(), MethodM.getLOC(),(MethodM.getLOC()>120)?1:0);
		if(MethodM.getCC()>15)
			pollution.addPollution("Cyclomatic Complexity", MethodM.getElement().getName(), MethodM.getCC(),(MethodM.getCC()>20)?1:0);

		return pollution;
	}
	
	/**
	 * 오염도차트를 그리기 위한 객체를 리턴한다. 
	 * @param commit
	 * @param NSLeft
	 * @param NSRight
	 * @return
	 */
	public PollutionChart getPollutionChart(ProjectCommit commit, int NSLeft,int NSRight)
	{
		PollutionChart chart = getPollutionList(commit,NSLeft,NSRight).getCountPollution();
		
		return chart;
	}
	
	
	
}
