package stanly.server.Analysis.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;

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
import stanly.server.Analysis.Model.*;
import stanly.server.Analysis.Model.Metric.AttributeMetric;
import stanly.server.Analysis.Model.Metric.ClassMetric;
import stanly.server.Analysis.Model.Metric.ElementNodeMetric;
import stanly.server.Analysis.Model.Metric.LibraryMetric;
import stanly.server.Analysis.Model.Metric.MethodMetric;
import stanly.server.Analysis.Model.Metric.PackageMetric;
import stanly.server.Analysis.Model.Metric.PackageSetMetric;
import stanly.server.Analysis.Model.Metric.ProjectMetric;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;

@Service("analysisService")
@Transactional
public class AnalysisService {

	protected static final Logger logger = Logger.getLogger("AnalysisService");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	private ElementDAO nodeDao;
	
	public ProjectElementNode insertElement(String name, String paretnName, int nSLeft, int nSRight, NodeType type)
	{
		ProjectElementNode node = new ProjectElementNode(name,paretnName,nSLeft,nSRight,type);
		nodeDao.insertElement(node);
	
		return node;
	}
	public ProjectElementNode InsertElement(ProjectElementNode e)
	{
		return nodeDao.insertElement(e);
	}
	public ProjectElementNode createElement(String name, String paretnName, int nSLeft, int nSRight, NodeType type)
	{
		ProjectElementNode node = new ProjectElementNode(name,paretnName,nSLeft,nSRight,type);	
		return node;
	}
	public ProjectElementNode createElement(ElementNode clientNode)
	{
		ElementNode parentNode = clientNode.getParent();
		ProjectElementNode serverNode = null;
		try
		{
			serverNode = createElement(clientNode.getName(), parentNode == null ? "" : parentNode.getFullName(),
										 				  parentNode.getLeftSideValue(), parentNode.getRightSideValue(), 
										 				  ConvertElementNodeType(clientNode.getType()));
			InputMetricDatatoProjectElementNode(clientNode,serverNode);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage() + "\n" + e.getStackTrace().toString());
		}
		
		return serverNode;
	}
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
		InsertIterationElementNode(data.getRootNode());
		
		return data.getRootNode();
	}
	/**
	 * RootNode에서 순회하면서 DB 넣기 
	 * @since 2013. 3. 10.오후 2:28:53
	 * @author JeongSeungsu
	 * @param clientnode
	 */
	private void InsertIterationElementNode(ElementNode clientnode)
	{
		InsertElement(createElement(clientnode));
		
		for(ElementNode childnode : clientnode.getChildren())
			InsertIterationElementNode(childnode);
	}
	
	
	
	
	@Async
	public Future<String> analysisNode(ProjectCommit commit)
	{
		try{
			logger.info("ProjectInfo insert");
			
			
	

		}catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return new AsyncResult<String>("완료 ㅎㅎ");
	}
}
