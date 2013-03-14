package stanly.server.GitProject.Controller;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stanly.server.GitProject.Git.GitControl;
import stanly.server.GitProject.Service.GitControlService;
import stanly.server.GitProject.Service.ProjectInfoService;
import stanly.server.GitProject.json.ProjectStateJSON;
import stanly.server.GitProject.json.ResultData;

import com.google.gson.Gson;

@RequestMapping("/project")
@Controller(value = "projectController")
public class ProjectController {
	protected static Logger logger = Logger.getLogger("projectcontroller");
	
	private String getProjectName(String URL)
	{
		  String[]  arr =  URL.split("/");
		  String[] Name =   arr[arr.length-1].split("\\.");
		  
		return Name[0];
	}
	@Autowired
	@Qualifier("gitControlService")
	private GitControlService gitControlService;
	
	@Autowired
	@Qualifier("projectinfoService")
	private ProjectInfoService projectService;

    @RequestMapping(value = "/GitClone", method=RequestMethod.POST)
    public String GitClone(@RequestParam("URL") String url,@RequestParam(value="Name" , required=false) String name, HttpSession session) throws Exception {
    	
    		String ProjectName = (name!=null) ? name: getProjectName(url);
    		Future<GitControl> git = gitControlService.GitClone(url,ProjectName );
    		session.setAttribute("Git", git);
    		session.setAttribute("ProjectName", ProjectName);
    		return "analysis/cloning";
    }
    
    @RequestMapping(value = "/IsProject", method=RequestMethod.GET)
    @ResponseBody
    public String IsProject(@RequestParam("URL") String url,@RequestParam(value="Name" , required=false) String name)
    	{
    		String ProjectName = (name!=null) ? name: getProjectName(url);
    		System.out.println(url);
    		ProjectStateJSON	 state = new ProjectStateJSON(projectService.getProjectState(url, ProjectName).name());
    		Gson gson = new Gson();
    		
    		return gson.toJson(state);
    	}
    
    @RequestMapping(value = "/GitClone/IsDone.json", method=RequestMethod.GET)
    @ResponseBody 
    public String IsGitGloneDone(HttpSession session, HttpServletResponse response)
    {
    		
    		response.setContentType("application/json");
    		Gson gson = new Gson();
    		String pName = (String)session.getAttribute("ProjectName");
    		ResultData result = new ResultData(false,pName);
    		try{
    			
    			Future<GitControl> git = (Future<GitControl>) session.getAttribute("Git");
    		
    		if(git.isDone()){
    			result.setResult(true);
    			session.removeAttribute("Git");
    		}
    		
    		}catch(Exception e){
    			return gson.toJson(result);
    		}
    		return gson.toJson(result);
    }
}
