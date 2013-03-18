package stanly.server.Analysis.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.FieldDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.MethodDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageSetDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.AttributeMetric;
import stanly.server.Analysis.Model.Metric.ClassMetric;
import stanly.server.Analysis.Model.Metric.LibraryMetric;
import stanly.server.Analysis.Model.Metric.MethodMetric;
import stanly.server.Analysis.Model.Metric.PackageMetric;
import stanly.server.Analysis.Model.Metric.PackageSetMetric;
import stanly.server.Analysis.Model.Metric.ProjectMetric;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.GitProject.Service.ProjectInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/context-root.xml")
public class StanlyPMDTest {
	protected static final Logger logger = Logger.getLogger("StanlyPMD");
	@Resource(name="analysisService")
	private AnalysisService analysis;
	@Resource(name="projectinfoService")
	private ProjectInfoService projectService;
	private ProjectInfo info; 
	
	@Before
	public void BeforeTest()
	{
		projectService.addProject("www.asdf.ac.kr", "/Users/Karuana/Documents/STANly/pmd_STANly", "PMD");
		info = projectService.getProjectInfo("PMD");
		logger.info("Before Setting = "+info.toString());
		projectService.addCommit(info, new Date(), "init Commit", "Karuana");
	}
	
	@Test
	public void TestInsertElementNode()
	{
		ProjectCommit commit= projectService.getLastCommit(info);
		ElementNode rootnode = analysis.AnalysisElementNode(commit, info.getLocation());
		List<ProjectElementNode> elementlist =  analysis.getTree(commit);
		assertNotNull(elementlist);
		assertNotNull(rootnode);
		//총 갯수중 10개 뽑아서 확인
		// 10개 안되면 걍 다돌림
		int listSize = elementlist.size();
		int interbal = 0;
		if(listSize < 10)
			interbal = 1;
		else
			interbal = listSize / 10;
		
		ElementNode clientcompareNode = null;
		try {
			
			for (int i = 0; i < listSize; i += interbal) 
			{
				ProjectElementNode serverCompareNode = elementlist.get(i);
				clientcompareNode = FindNodeToName(serverCompareNode.getNSLeft(), rootnode);

				assertNotNull(clientcompareNode);

				assertEquals(serverCompareNode.getNSLeft(),
						clientcompareNode.getLeftSideValue());
				assertEquals(serverCompareNode.getNSRight(),
						clientcompareNode.getRightSideValue());

				assertTrue(CompareMetric(serverCompareNode, clientcompareNode));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void TestInsertRelation()
	{
		
	}
	private ElementNode FindNodeToName(int leftid, ElementNode node)
	{
		
		if(node.getLeftSideValue() == leftid)
			return node;
		
		ElementNode result = null;
		
		for(ElementNode childnode : node.getChildren())
		{
			result = FindNodeToName(leftid, childnode);
			if(result != null)
				return result;
		}
		
		return result;
	}
	public boolean CompareMetric(ProjectElementNode server,ElementNode client)
	{
		
		switch(client.getType())
		{
		case PROJECT:
			ProjectMetric projectmetric = (ProjectMetric)(server.getEMetric());
			if(CompareMetric(projectmetric,((ProjectDomain)client).metric))
				return true;
			else
				return false;
		case LIBRARY:
			LibraryMetric librarymetric = (LibraryMetric)(server.getEMetric());
			if(CompareMetric(librarymetric, ((LibraryDomain)client).metric))
				return true;
			else
				return false;
		case PACKAGE:
			PackageMetric packagemetric = (PackageMetric)(server.getEMetric());
			if(CompareMetric(packagemetric, ((PackageDomain)client).metric))
				return true;
			else
				return false;
		case PACKAGESET:
			PackageSetMetric packagesetmetric = (PackageSetMetric)(server.getEMetric());
			if(CompareMetric(packagesetmetric, ((PackageSetDomain)client).metric))
				return true;
			else
				return false;
		case CLASS:
		case ENUM:
		case INTERFACE:
		case ANNOTATION:
			ClassMetric classmetric = (ClassMetric)(server.getEMetric());
			if(CompareMetric(classmetric, ((ClassDomain)client).metric))
				return true;
			else
				return false;
		case FIELD:
			AttributeMetric attributemetric = (AttributeMetric)(server.getEMetric());
			if(CompareMetric(attributemetric, ((FieldDomain)client).metric))
				return true;
			else
				return false;
		case CONSTRUCTOR:
		case METHOD:
			MethodMetric methodmetric = (MethodMetric)(server.getEMetric());
			if(CompareMetric(methodmetric, ((MethodDomain)client).metric))
				return true;
			else
				return false;
		default:
			return false;
		}
	}
	public boolean CompareMetric(ProjectMetric serverMetric, net.sourceforge.pmd.lang.java.rule.stanly.metrics.ProjectMetric clientMetric)
	{
		boolean IsFail = true;
		if(serverMetric.getACDLibrary() != clientMetric.getACD())
			IsFail = false;
		if(serverMetric.getFat() != clientMetric.getFat())
			IsFail = false;
		if(serverMetric.getTangled() != clientMetric.getTangled())
			IsFail = false;
			
		return IsFail;
	}
	//몇개만..
	public boolean CompareMetric(LibraryMetric serverMetric, net.sourceforge.pmd.lang.java.rule.stanly.metrics.LibraryMetric clientMetric)
	{
		boolean IsFail = true;
		if(serverMetric.getACDPackage() != clientMetric.getACDPackage())
			IsFail = false;
		if(serverMetric.getAverageCBO() != clientMetric.getAverageCBO())
			IsFail = false;
		if(serverMetric.getDistanceAbsolute() != clientMetric.getDistanceAbsolute())
			IsFail = false;
		if(serverMetric.getLCOM() != clientMetric.getLCOM())
			IsFail = false;
		if(serverMetric.getDIT() != clientMetric.getDIT())
			IsFail = false;
		
		return IsFail;
	}
	public boolean CompareMetric(PackageMetric serverMetric, net.sourceforge.pmd.lang.java.rule.stanly.metrics.PackageMetric clientMetric)
	{
		boolean IsFail = true;
		if(serverMetric.getAfferentCoupling() != clientMetric.getAfferentCoupling())
			IsFail = false; 
		if(serverMetric.getEfferentCoupling() != clientMetric.getEfferentCoupling())
			IsFail = false;
		if(serverMetric.getNumberOfClasses() != clientMetric.getNumberOfClasses())
			IsFail = false;
		
		return IsFail;
	}
	public boolean CompareMetric(PackageSetMetric serverMetric, net.sourceforge.pmd.lang.java.rule.stanly.metrics.PackageSetMetric clientMetric)
	{
		boolean IsFail = true;
		if(serverMetric.getAverageCC() != clientMetric.getAverageCC())
			IsFail = false;
		if(serverMetric.getAverageELOC() != clientMetric.getAverageELOC())
			IsFail = false;
		if(serverMetric.getTangled() != clientMetric.getTangled())
			IsFail = false;
		
		return IsFail;
	}
	public boolean CompareMetric(ClassMetric serverMetric, net.sourceforge.pmd.lang.java.rule.stanly.metrics.ClassMetric clientMetric)
	{
		boolean IsFail = true;
		
		if(serverMetric.getFat() != clientMetric.getFat())
			IsFail = false;
		if(serverMetric.getNOC() != clientMetric.getNOC())
			IsFail = false;
		if(serverMetric.getWMC() != clientMetric.getWMC())
			IsFail = false;
		
		return IsFail;
	}
	public boolean CompareMetric(AttributeMetric serverMetric, net.sourceforge.pmd.lang.java.rule.stanly.metrics.AttributeMetric clientMetric)
	{
		boolean IsFail = true;
		
		if(serverMetric.getCC() != clientMetric.getCC())
			IsFail = false;
		if(serverMetric.getELOC() != clientMetric.getELOC())
			IsFail = false;
		if(serverMetric.getInstructions() != clientMetric.getInstructions())
			IsFail = false;
		
		return IsFail;
	}
	public boolean CompareMetric(MethodMetric serverMetric, net.sourceforge.pmd.lang.java.rule.stanly.metrics.MethodMetric clientMetric)
	{
		boolean IsFail = true;
		
		if(serverMetric.getLOC() != clientMetric.getLOC())
			IsFail = false;
		if(serverMetric.getCC() != clientMetric.getCC())
			IsFail = false;
		
		return IsFail;
	}
}
