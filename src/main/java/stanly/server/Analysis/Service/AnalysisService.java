package stanly.server.Analysis.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import net.sourceforge.pmd.lang.java.rule.Violation;
import net.sourceforge.pmd.lang.java.rule.ViolationController;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainComposition;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.Relations;
import net.sourceforge.pmd.lang.java.rule.stanly.StanlyAnalysisData;
import net.sourceforge.pmd.lang.java.rule.stanly.StanlyControler;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.FieldDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.MethodDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageSetDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.DAO.ElementDAO;
import stanly.server.Analysis.DAO.PollutionRankDAO;
import stanly.server.Analysis.DAO.RelationDAO;
import stanly.server.Analysis.DAO.StaticAnalysisDAO;
import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.AttributeMetric;
import stanly.server.Analysis.Model.Metric.ClassMetric;
import stanly.server.Analysis.Model.Metric.ElementNodeMetric;
import stanly.server.Analysis.Model.Metric.LibraryMetric;
import stanly.server.Analysis.Model.Metric.MethodMetric;
import stanly.server.Analysis.Model.Metric.PackageMetric;
import stanly.server.Analysis.Model.Metric.PackageSetMetric;
import stanly.server.Analysis.Model.Metric.ProjectMetric;
import stanly.server.Analysis.Model.Relation.NodeComposition;
import stanly.server.Analysis.Model.Relation.NodeRelation;
import stanly.server.Analysis.Model.Relation.Type.NodeRelationType;
import stanly.server.Analysis.Model.StaticAnalysis.Type.StaticAnalysisType;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;

/**
 * @author Karuana
 *	Analysis와 관련된 기능들을 모아둔 서비스 객체이다. pmd_STANly를 통해 분석된 정보를 디비에 실제적으로 삽입하는 일을 진행한다. 
 */
@Service("analysisService")
@Transactional
public class AnalysisService {

	protected static final Logger logger = Logger.getLogger("AnalysisService");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	/**
	 * 엘리먼트 노드를 DB에서 컨트롤하기 위한 DAO 객체 
	 */
	@Autowired
	private ElementDAO nodeDao;
	
	/**
	 * 관계를 컨트롤하기 위한 DAO 객체 
	 */
	@Autowired
	private RelationDAO relationDao;
	
	/**
	 * 정적 분석을 위한 DAO 객체
	 */
	@Autowired
	private StaticAnalysisDAO staticanalysisDao;

	@Autowired
	private ProjectDAO pDAO;
	
	@Autowired
	private PollutionRankDAO rankDAO;
	
	/**
	 * 노드를 생성해 추가한다.
	 * @param name 노드 명 
	 * @param paretnName 부모의 이름 
	 * @param nSLeft 
	 * @param nSRight
	 * @param type 노드 타입 
	 * @return 생성된 노
	 */
	public ProjectElementNode insertElement(String name, String paretnName, int nSLeft, int nSRight, NodeType type)
	{
		ProjectElementNode node = new ProjectElementNode(name,paretnName,nSLeft,nSRight,type);
		nodeDao.insertElement(node);
	
		return node;
	}
	/**
	 *  주어진 노드를 삽입한다. 
	 * @param e
	 * @return
	 */
	public ProjectElementNode InsertElement(ProjectElementNode e)
	{
		return nodeDao.insertElement(e);
	}
	/**
	 * 노드 객체를 생성한다. 
	 * @param commitID
	 * @param name
	 * @param paretnName
	 * @param nSLeft
	 * @param nSRight
	 * @param type
	 * @return
	 */
	public ProjectElementNode createElement(ProjectCommit commitID, String name, String paretnName, int nSLeft, int nSRight, NodeType type)
	{
		ProjectElementNode node = new ProjectElementNode(name,paretnName,nSLeft,nSRight,type);	
		node.setCommit(commitID);
		return node;
	}
	/**
	 * 노드 객체를 생성한다. 
	 * @param commitID
	 * @param clientNode
	 * @return
	 */
	public ProjectElementNode createElement(ProjectCommit commitID, ElementNode clientNode)
	{
		ElementNode parentNode = clientNode.getParent();
		ProjectElementNode serverNode = null;
		try
		{
		
			String nodeName = ConvertServerNodeName(clientNode);
			String ParentName = "";
			if(parentNode != null)
				ParentName = ConvertServerNodeName(parentNode);
			
	
			serverNode = createElement(commitID, nodeName,
												parentNode == null ? "RootNode" : ParentName,
										 		clientNode.getLeftSideValue(), clientNode.getRightSideValue(), 
										 		ConvertElementNodeType(clientNode.getType()));
			
			InputMetricDatatoProjectElementNode(clientNode,serverNode);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage() + "\n" + e.getStackTrace().toString());
			//logger.error("에러다");
		}
		
		return serverNode;
	}



	public boolean AddNode(ProjectCommit commit)
	{
		
		//Test Case 만들기    
		try{


			ArrayList<ProjectElementNode> list = new ArrayList<ProjectElementNode>();
			list.add(new ProjectElementNode("stanly.server", "NONE", 1, 6,NodeType.PACKAGE));
			list.add(new ProjectElementNode("stanly.server.Analysis", "stanly.server", 2, 3,NodeType.PACKAGE));
			list.add(new ProjectElementNode("stanly.server.GitProject", "stanly.server", 4, 5,NodeType.PACKAGE));
			// Data Save
			for(int i=0;i<list.size();i++)
			{
				list.get(i).setCommit(commit);
				nodeDao.insertElement(list.get(i));
			}

		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	public List<ProjectElementNode> getTree(ProjectCommit CommitID)
	{
		return nodeDao.getNodeTree(CommitID);
	}
	public ProjectElementNode getNode(ProjectCommit commitID, String projectName)
	{
		return nodeDao.getNode(commitID, projectName);
	}
	
	/**
	 * 데이터 가져와서 server에 노드 생성하고 DB넣기
	 * @since 2013. 3. 10.오후 2:27:07
	 * @author JeongSeungsu
	 * @param commitID
	 * @param path
	 */
	public ElementNode AnalysisElementNode(ProjectCommit commitID, String path)
	{
		StanlyAnalysisData data = StanlyControler.StartAnalysis(path);
		InsertIterationElementNode(commitID,data.getRootNode());
		InputRelation(commitID,data.getRelationList());				//이거 테스트 검증이 필요함

		//Composition 뷰를 위한 자료구조 추가 (NodeComposition)
		InputNodeComposition(commitID,data.getCompositionList());
		
		//정적 분석 추가
		InputStaticAnalysis(commitID,ViolationController.getViolationList());
		
		return data.getRootNode();
	}
	private void InsertStaticAnalysis(ProjectCommit commitID, Integer violationType,String sourcePath, int sourceLine,int Nsleft, String message)
	{
		StaticAnalysisType type = StaticAnalysisType.BASIC;
		
		if(violationType == 0)
			type = StaticAnalysisType.BASIC;
		else if(violationType == 1)
			type = StaticAnalysisType.NAMING;
		 
		staticanalysisDao.insertSAMetric(type, sourcePath,sourceLine, Nsleft, message, commitID);
	}
	private void InputStaticAnalysis(ProjectCommit commitID, List<Violation> ViolationList)
	{
		try
		{
			for(Violation violation : ViolationList)
			{
				InsertStaticAnalysis(commitID,violation.getViolationType(),violation.getSourcePath(),
									 violation.getSourceLine(),violation.getDomainLeftValue(),violation.getMessage());
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage() + "\n" +e.getStackTrace()); 
		}
	}
	
	
	private void InsertNodeComposition(ProjectCommit commit,int srcID, int tarID,int count, 
			NodeRelationType type)
	{
		relationDao.insertComposition(new NodeComposition(commit, srcID, tarID, count, type));
	}
	
	private void InputNodeComposition(ProjectCommit commitID, List<DomainComposition> nodeCompositionList)
	{
		try
		{
			for(DomainComposition nodecomposition : nodeCompositionList)
			{
				NodeRelationType type = ConvertClientRelationTypeToServerRelationType(nodecomposition.getDelegateType());
				InsertNodeComposition(commitID, nodecomposition.getSourceID(), nodecomposition.getTargetID(), nodecomposition.getRelationCount(), type);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage() + "\n" +e.getStackTrace()); 
		}
	}
	
	/**
	 * 
	 * @param clientNode
	 * @param serverNode
	 * @throws Exception
	 */
	private void InputMetricDatatoProjectElementNode(ElementNode clientNode, ProjectElementNode serverNode) throws Exception
	{
		ElementNodeMetric metric = serverNode.addElementMetric();
		
		switch(serverNode.getType())
		{
		case PROJECT:
			InputProjectSetMetric((ProjectMetric)metric, (ProjectDomain)clientNode);
			break;
		case LIBRARY:
			InputLibraryMetric((LibraryMetric)metric, (LibraryDomain)clientNode);
			break;
		case PACKAGE:
			InputPackageMetric((PackageMetric)metric, (PackageDomain)clientNode);
			break;
		case PACKAGESET:
			InputPackageSetMetric((PackageSetMetric)metric, (PackageSetDomain)clientNode);
			break;
		case CLASS:
		case ENUM:
		case INTERFACE:
		case ANNOTATION:
			InputClassMetric((ClassMetric)metric, (ClassDomain)clientNode);
			break;
		case FIELD:
			InputAttributeMetric((AttributeMetric)metric, (FieldDomain)clientNode);
			break;
		case CONSTRUCTOR:
		case METHOD:
			InputMethodMetric((MethodMetric)metric, (MethodDomain)clientNode);
			break;
		default:
			throw new Exception("Not Fount Type : " + serverNode.getType().toString() + "");
		}
		metric.setRate();
		
	}
	private void InputAttributeMetric(AttributeMetric metric, FieldDomain node)
	{
		net.sourceforge.pmd.lang.java.rule.stanly.metrics.AttributeMetric clientMetric = node.metric;
		
		metric.setCC(clientMetric.getCC());
		metric.setELOC(clientMetric.getELOC());
		metric.setInstructions(clientMetric.getInstructions());
	}
	private void InputClassMetric(ClassMetric metric, ClassDomain node)
	{
		net.sourceforge.pmd.lang.java.rule.stanly.metrics.ClassMetric clientMetric = node.metric;
		
		metric.setAfferentCoupling(clientMetric.getAfferentCoupling());
		metric.setCBO((int)clientMetric.getCBO());
		metric.setClasses(clientMetric.getClasses());
		metric.setDIT(clientMetric.getDIT());
		metric.setEfferentCoupling(clientMetric.getEfferentCoupling());
		metric.setFields(clientMetric.getFields());
		metric.setLCOM(clientMetric.getLCOM());
		metric.setMethods(clientMetric.getMethods());
		metric.setWMC(clientMetric.getWMC());
		metric.setFat(clientMetric.getFat());
		metric.setLOC(clientMetric.getLOC());
		metric.setNOC(clientMetric.getNOC());
		metric.setRFC(clientMetric.getRFC());
		
	}
	private void InputLibraryMetric(LibraryMetric metric, LibraryDomain node)
	{
		net.sourceforge.pmd.lang.java.rule.stanly.metrics.LibraryMetric clientMetric = node.metric;
		
		
		metric.setACDPackage(clientMetric.getACDPackage());
		metric.setACDUnit(clientMetric.getACDUnit());
		metric.setFatPackages(clientMetric.getFatPackages());
		metric.setFatUnits(clientMetric.getFatUnits());
		metric.setNumberOfClass(clientMetric.getNumberOfClass());
		metric.setPackages(clientMetric.getPackages());
		metric.setTangled(clientMetric.getTangled());
		metric.setTotalCBO(clientMetric.getTotalCBO());
		metric.setTotalDistance(clientMetric.getTotalDistance());
		metric.setTotalDistanceAbsolute(clientMetric.getTotalDistanceAbsolute());
		metric.setTotalDIT((int)clientMetric.getTotalDIT());
		metric.setTotalLCOM(clientMetric.getTotalLCOM());
		metric.setTotalNOC(clientMetric.gettotalNOC());
		metric.setTotalRFC(clientMetric.getTotalRFC());
		metric.setTotalWMC(clientMetric.getTotalWMC());
		metric.setUnit(clientMetric.getUnit());
	}
	private void InputMethodMetric(MethodMetric metric, MethodDomain node)
	{
		net.sourceforge.pmd.lang.java.rule.stanly.metrics.MethodMetric clientMetric = node.metric;
		
		metric.setLOC(clientMetric.getLOC());
		metric.setCC(clientMetric.getCC());
		
	}
	private void InputPackageMetric(PackageMetric metric, PackageDomain node)
	{
		net.sourceforge.pmd.lang.java.rule.stanly.metrics.PackageMetric clientMetric = node.metric;
		
		metric.setUnits(clientMetric.getUnits());
		metric.setAbstractness(clientMetric.getAbstractness());
		metric.setACDPerUnit(clientMetric.getACDPerUnit());
		metric.setAfferentCoupling(clientMetric.getAfferentCoupling());
		metric.setDistance(clientMetric.getDistance());
		metric.setEfferentCoupling(clientMetric.getEfferentCoupling());
		metric.setFat(clientMetric.getFat());
		metric.setInstability(clientMetric.getInstability());
		metric.setLOC(clientMetric.getLOC());
		metric.setLCOM(clientMetric.getLCOM());
		metric.setNumberOfAbstract(clientMetric.getNumberOfAbstract());
		metric.setNumberOfClass(clientMetric.getNumberOfClass());
		metric.setNumberOfClasses(clientMetric.getNumberOfClasses());
		metric.setNumberOfFields(clientMetric.getNumberOfFields());
		metric.setNumberOfMethods(clientMetric.getNumberOfMethods());
		metric.setTotalCBO(clientMetric.getTotalCBO());
		metric.setTotalCC(clientMetric.getTotalCC());
		metric.setTotalDIT(clientMetric.getTotalDIT());
		metric.setTotalLCOM(clientMetric.getTotalLCOM());
		metric.setTotalNOC(clientMetric.getTotalNOC());
		metric.setTotalRFC(clientMetric.getTotalRFC());
	}
	private void InputPackageSetMetric(PackageSetMetric metric, PackageSetDomain node)
	{
		net.sourceforge.pmd.lang.java.rule.stanly.metrics.PackageSetMetric clientMetric = node.metric;

		metric.setFat(clientMetric.getFat());
		metric.setNumberOfClass(clientMetric.getNumberOfClass());
		metric.setNumberOfClasses(clientMetric.getNumberOfClasses());
		metric.setNumberOfFields(clientMetric.getNumberOfFields());
		metric.setNumberOfMethods(clientMetric.getNumberOfMethods());
		metric.setNumberOfPackages(clientMetric.getNumberOfPackages());
		metric.setTangled(clientMetric.getTangled());
		metric.setTotalCC(clientMetric.getTotalCC());
		metric.setTotalELOC(clientMetric.getTotalELOC());
		metric.setTotalUnit(clientMetric.getTotalUnit());
	}
	private void InputProjectSetMetric(ProjectMetric metric, ProjectDomain node)
	{
		net.sourceforge.pmd.lang.java.rule.stanly.metrics.ProjectMetric clientMetric = node.metric;
		
		metric.setACDLibrary(clientMetric.getACD());
		metric.setFat(clientMetric.getFat());
		metric.setLibraries(clientMetric.getLibraries());
		metric.setTangled(clientMetric.getTangled());
	}
	
	private NodeType ConvertElementNodeType(ElementNodeType clientType) throws Exception
	{
		switch(clientType)
		{
			case PROJECT:
				return NodeType.PROJECT;
			case LIBRARY:
				return NodeType.LIBRARY;
			case PACKAGE:
				return NodeType.PACKAGE;
			case PACKAGESET:
				return NodeType.PACKAGESET;
			case CLASS:
				return NodeType.CLASS;
			case ENUM:
				return NodeType.ENUM;
			case INTERFACE:
				return NodeType.INTERFACE;
			case FIELD:
				return NodeType.FIELD;
			case CONSTRUCTOR:
				return NodeType.CONSTRUCTOR;
			case METHOD:
				return NodeType.METHOD;
			case ANNOTATION:
				return NodeType.ANNOTATION;
			default:
				throw new Exception("Not Fount Type : " + clientType.toString() + "");
		}
	}
	
	
	
	
	private void InputRelation(ProjectCommit commitID,List<DomainRelation> relationList) 
	{
		try
		{
		for(DomainRelation relation : relationList)
		{
			String SourceName = "";
			String TargetName = "";
			NodeRelationType type = ConvertClientRelationTypeToServerRelationType(relation.getRelation());
			
			SourceName = ConvertServerNodeName(relation.getSourceNode());
			TargetName = ConvertServerNodeName(relation.getTargetNode());
			
			InsertNodeRelation(SourceName,TargetName,commitID,type);
		}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage() + "\n" +e.getStackTrace()); 
		}
		
	}
	private void InsertNodeRelation(String srcName, String tarName, ProjectCommit commit,
			NodeRelationType type)
	{
		relationDao.insertRelation(new NodeRelation(srcName,tarName,commit,type));
	}
	private NodeRelationType ConvertClientRelationTypeToServerRelationType(Relations relation) throws Exception
	{
		switch(relation)
		{
		case EXTENDS:
			return NodeRelationType.EXTENDS;	
		case IMPLEMENTS:
			return NodeRelationType.IMPLEMENTS;		//요기 문제다 혜성아... 2가지 구분해줘야될듯? 아닌가 // 바꿈요 
		case CONTAINS:
			return NodeRelationType.CONTAINS;
		case RETURNS:
			return NodeRelationType.RETURNS;
		case HASPARAM:
			return NodeRelationType.HAS_PARAM;
		case THROWS:
			return NodeRelationType.THROWS;
		case CALLS:
			return NodeRelationType.CALLS;
		case ACCESSES:
			return NodeRelationType.ACCESSES;
		case ISOFTYPE:
			return NodeRelationType.IS_OF_TYPE;
		case REFERENCES:
			return NodeRelationType.REFERENCESE;
		case UNKNOWN:
			throw new Exception("Unknown Type이 들어왔다.");
		default:
			throw new Exception("모르는 타입이 들어왔다.");
		}
	}
	/**
	 * RootNode에서 순회하면서 DB 넣기 
	 * @since 2013. 3. 10.오후 2:28:53
	 * @author JeongSeungsu
	 * @param clientnode
	 */
	private void InsertIterationElementNode(ProjectCommit commitID, ElementNode clientnode)
	{
		InsertElement(createElement(commitID, clientnode));
		
		int i=0;
		for(ElementNode childnode : clientnode.getChildren())
		{	
			InsertIterationElementNode(commitID, childnode);
			if(i++==50)
			{
				nodeDao.DataFlush();
				i=0;
			}
		}
	}
	private String ConvertServerNodeName(ElementNode clientNode)
	{
		String serverName = "";
		if(clientNode.getType() == ElementNodeType.METHOD ||
			clientNode.getType() == ElementNodeType.CONSTRUCTOR)
			serverName = ((MethodDomain)clientNode).getMethodFullName();
		else if(clientNode.getType() == ElementNodeType.LIBRARY)
			serverName = clientNode.getName();
		else
			serverName = clientNode.getFullName();
		
		return serverName;
	}
	
	
	
	@Async
	public Future<String> analysisNode(ProjectCommit commit)
	{
		String RootName=null;
		try{
			logger.info("ProjectInfo insert");
			RootName = AnalysisElementNode(commit, commit.getPInfo().getLocation()).getFullName();
			
			ProjectInfo pinfo = commit.getPInfo();
			
			pinfo.setFAT_RANK(rankDAO.getProjectFATRate());
			pinfo.setCP_RANK(rankDAO.getProjectCPRate());
			pinfo.setCoplingRANK(rankDAO.getProjectCouplingRate());
			pinfo.setName_RANK(rankDAO.getNameStaticAnalysisRank());
			pinfo.setBasic_RANK(rankDAO.getBasicStaticAnalysisRank());
			pinfo.setLastDate(commit.getUpdateDate());
			pDAO.updateProjectInfo(pinfo);
			
		}catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return new AsyncResult<String>(RootName);
	}
}
