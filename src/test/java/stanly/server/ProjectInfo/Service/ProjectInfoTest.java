package stanly.server.ProjectInfo.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import stanly.server.ProjectInfo.Model.ProjectInfo;
import stanly.server.sample.Data.Person;
import stanly.server.sample.Service.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/context-root.xml")
public class ProjectInfoTest {

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
		assertEquals(1,person.size());
	}

	
	@Test
	public void TestInsertAndUpdate()
	{
		assertTrue(projectService.add("http://NULL.com","/Users/Karuana/Documents/STANly","pmd_Stanly"));
		ProjectInfo info = projectService.getProjectInfo("pmd_Stanly");
		System.out.println(info.getName()+"  "+info.getLocation());
		assertEquals("pmd_Stanly", info.getName());
	}

}
