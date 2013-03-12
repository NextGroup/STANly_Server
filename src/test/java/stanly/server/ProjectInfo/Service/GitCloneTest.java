package stanly.server.ProjectInfo.Service;

import java.io.File;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import stanly.server.GitProject.Controller.ProjectController;
import stanly.server.GitProject.Git.GitControl;
import stanly.server.GitProject.Service.GitControlService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/context-root.xml")
public class GitCloneTest {

	private MockMvc mockMvc;
	
	@Resource(name="gitControlService")
	private GitControlService GitService;
	@Autowired
	private ProjectController porject;
	@Before
	public void SetUp()
	{
		 mockMvc = MockMvcBuilders.xmlConfigSetup(new String[] {"classpath:/config/spring/context-root.xml", "file:webapp/WEB-INF/config/springmvc/dispatcher-servlet.xml"}).build();

	}
	
	@Test
	public void GitCloneTest()
	{
		   Future<GitControl> git = GitService.GitClone("https://github.com/karuana/ithaka-digraph.git", "digraph");
		   while(!git.isDone()){
			   System.out.println("1");
			   try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		   }
	}
	
	@Test
	public void TestController()
	{


	}
	
	
	@After
	public void FolderReset()
	{
		File folder = new File("../GitProject");
		deleteFolder(folder);
	}
	public boolean deleteFolder(File targetFolder){
		 
	      File[] childFile = targetFolder.listFiles();
	      boolean confirm = false;
	      int size = childFile.length;
	 
	      if (size > 0) {
	 
	          for (int i = 0; i < size; i++) {
	 
	              if (childFile[i].isFile()) {
	 
	                  confirm = childFile[i].delete();
	 

	                  
	              } else {
	 
	                  deleteFolder(childFile[i]);
	 
	              }
	 
	          }
	 
	      }
	 
	   
	 
	      targetFolder.delete();
	 
	 
	      
	      return (!targetFolder.exists());
	  
	    
	}
}
