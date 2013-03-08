package stanly.server.ProjectInfo.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.GitProject.Service.ProjectInfoService;
import stanly.server.sample.Data.Person;
import stanly.server.sample.Service.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/context-test.xml")
public class ProjectInfoTest {
	protected static final Logger logger = Logger.getLogger("Test");
	@Resource(name="projectinfoService")
	private ProjectInfoService projectService;
	
	@Resource(name="personService")
	private PersonService personService;
	
	
	@Test
	public void TestPerson()
	{
		
		personService.add("오","혜성",(double) 1000002.0f);
		List<Person> person = personService.getAll();
		System.out.println(person.get(0).getLastname());
		logger.error(person.size());
		assertTrue(person.size()!=0);
	}

	
	@Test
	public void TestProjectAndCommit()
	{
		try {
		projectService.addProject("http://NULL.com","/Users/Karuana/Documents/STANly","pmd_Stanly");
		ProjectInfo info = projectService.getProjectInfo("pmd_Stanly");
		assertEquals("pmd_Stanly", info.getName());
		java.text.SimpleDateFormat formatter = 
				new java.text.SimpleDateFormat ("yyyy-MM-dd HH:mm:ss", java.util.Locale.KOREA); 
				
		java.util.Date	d = formatter.parse("2004-09-24 11:12:21");
		
		projectService.addCommit(info,d, "Init Commit", "Karuana");
		projectService.addCommit(info,new Date(), "Second Commit", "Karuana");
		
		logger.error(info.getName()+"  "+info.getLocation());
		
		List<ProjectCommit> commitList = projectService.getCommitList("pmd_Stanly");

		assertFalse(commitList == null);
		assertEquals("Second Commit",commitList.get(0).getMessage());
		assertEquals("pmd_Stanly", info.getName());
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
