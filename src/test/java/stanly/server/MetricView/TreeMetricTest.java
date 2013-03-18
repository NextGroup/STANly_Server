package stanly.server.MetricView;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import stanly.server.MetricView.DAO.MetricSearchDAO;
import stanly.server.MetricView.Service.MetricViewService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/context-root.xml")
public class TreeMetricTest {
	protected static final Logger logger = Logger.getLogger("StanlyPMD");
	@Autowired
	private MetricViewService service;
	
	@Autowired
	private MetricSearchDAO dao;
	
	@Test
	public void TreeNodeTest()
	{
		String val = service.getTreeNode("PMD", 0);
		assertNotNull(val);
		logger.info(val);
		val = service.getTreeNode("PMD", 3);
		logger.info(val);
	}
	@Test 
	public void nodeRelation()
	{
		String relation = service.getRelation("PMD", "net.sourceforge.pmd.lang","net.sourceforge.pmd.lang.ast.Node");
		assertNotNull(relation);
		logger.info(relation);
		
	}
	@Test
	public void MetricMertricTest(){
		//TestCase 만들
	}
}
