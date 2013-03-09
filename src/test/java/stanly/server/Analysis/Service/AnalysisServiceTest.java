package stanly.server.Analysis.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import stanly.server.Analysis.Model.ElementNode;
import stanly.server.Analysis.Model.Metric.PackageMetric;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.GitProject.Service.ProjectInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/context-test.xml")
public class AnalysisServiceTest {
	protected static final Logger logger = Logger.getLogger("Test");
	@Resource(name="analysisService")
	private AnalysisService analysis;
	@Resource(name="projectinfoService")
	private ProjectInfoService projectService;
	
	private ProjectInfo info; 
	
	@Before
	public void TestProjectSetUp()
	{
		projectService.addProject("www.sejong.ac.kr", "/acra/Type", "Logdog");
		info = projectService.getProjectInfo("Logdog");
		logger.info("Before Setting = "+info.toString());
		projectService.addCommit(info, new Date(), "init Commit", "Karuana");
	}
	@Test
	public void TestGetCommit()
	{
		//프로젝트 생성 
		
		ProjectCommit TestCommit = projectService.getCommitList("Logdog").get(0);
		logger.info("GetCommit "+TestCommit.toString());
		assertEquals("Logdog",TestCommit.getProjectInfo().getName());
		
	}
	
	@Test
	public void TestSetElements()
	{
		ProjectCommit TestCommit = projectService.getLastCommit(info);
		assertTrue(analysis.AddNode(TestCommit));
		List<ElementNode> Tree = analysis.getTree(TestCommit);
	
		assertNotNull(Tree);
		assertTrue(Tree.size()!=0);
		for(int i=0;i<Tree.size();i++)
			logger.info(Tree.get(i).toString());
	}
	@Test
	public void TestMetric()
	{
		ProjectCommit TestCommit = projectService.getLastCommit(info);
		ElementNode node = analysis.createElement("TestStanly.Server", "NONE", 1, 2, NodeType.PACKAGE);
		node.setCommit(TestCommit);
		PackageMetric metric = (PackageMetric)node.addElementMetric();
		metric.addCBO(10);
		metric.addCC(5);
		metric.addDIT(20);
		metric.addLCOM(5);
		metric.addLOC(20);
		metric.addNOC(20);
		metric.addNumberOfAbstract(5);
		metric.addNumberOfClass(25);
		metric.addNumberOfClasses(5);
		metric.addNumberOfFields(5);
		metric.addNumberOfMethods(10);
		metric.addRFC(5);
		metric.addUnits(4);
		logger.info("isert Node"+node.getName());
		analysis.InsertElement(node);
		node = analysis.getNode(TestCommit, "TestStanly.Server");
		logger.info("isert End"+node);
		PackageMetric TestMetric = (PackageMetric)node.getEMetric();
		assertEquals("TestStanly.Server",node.getName());
		logger.info("Hello "+((PackageMetric)node.getEMetric()).getNumberOfAbstract());
		assertEquals(5, TestMetric.getNumberOfAbstract());
		assertEquals(NodeType.PACKAGE,TestMetric.getType());
	}
	
	
	
}
