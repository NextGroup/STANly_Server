package stanly.server.Analysis.Service;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import stanly.server.Analysis.DAO.RelationDAO;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.GitProject.Service.ProjectInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/context-test.xml")
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
		projectService.addProject("www.sejong.ac.kr", "/acra/Type", "Logdog");
		info = projectService.getProjectInfo("Logdog");
		logger.info("Before Setting = "+info.toString());
		projectService.addCommit(info, new Date(), "init Commit", "Karuana");
	}
	
	@Test
	public void TestCase1()
	{
//		projectService.getLastCommit(info);
	//	analysis.getTree(CommitID)
		//assertEquals(1,1);
		
	}

}
