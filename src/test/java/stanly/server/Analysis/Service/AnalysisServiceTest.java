package stanly.server.Analysis.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
		projectService.add("www.sejong.ac.kr", "/acra/Type", "Logdog");
		info = projectService.getProjectInfo("Logdog");
	}
	@Test
	public void TestGetCommit()
	{
		//프로젝트 생성 
		
		ProjectCommit TestCommit = analysis.getCommit(info);
		
		assertEquals("Logdog",TestCommit.getProjectInfo().getName());
		
	}
	
	@Test
	public void TestSetElements()
	{
		ProjectCommit TestCommit = analysis.getCommit(info);
		assertTrue(analysis.AddNode(TestCommit));
		List Tree = analysis.getTree(TestCommit);
	
		assertNotNull(Tree);
		assertTrue(Tree.size()!=0);
		for(int i=0;i<Tree.size();i++)
			logger.info(Tree.get(i).toString());
	}
	
	
	
}
