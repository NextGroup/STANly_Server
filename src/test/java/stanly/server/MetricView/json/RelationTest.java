package stanly.server.MetricView.json;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import stanly.server.MetricView.Json.Relation;
import stanly.server.MetricView.Json.RelationList;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/context-test.xml")
public class RelationTest {
	private Gson gson;
	protected static final Logger logger = Logger.getLogger("RelationTest");
	@Before
	public void Setup()
	{
		gson = new Gson();
		
	}
	
	@Test
	public void RelationChk()
	{
		RelationList RTest = new RelationList();
		
		RTest.addRelation(new Relation("com.sejong.Analysis.TestXml","com.sejong.Controller.SpringController","EXTENDS"));
		RTest.addRelation(new Relation("com.sejong.Analysis.python","com.sejong.Controller.MacController","CONTAINS"));
		RTest.addRelation(new Relation("com.sejong.Analysis.TestJson","com.sejong.Controller.WindowController","Has_a_Parm"));

		RTest.addRelation(new Relation("com.sejong.Analysis.C++","com.sejong.Controller.WIFIController","ACCESS"));
		
		String json = gson.toJson(RTest);
		logger.info(json);
		assertNotNull(json);
		
	}

}
