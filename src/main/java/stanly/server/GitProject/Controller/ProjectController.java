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
import stanly.server.GitProject.json.ResultData;

import com.google.gson.Gson;

@RequestMapping("/project")
@Controller(value = "projectController")
public class ProjectController {
	protected static Logger logger = Logger.getLogger("controller");
	
	@Autowired
	@Qualifier("gitControlService")
	private GitControlService gitControlService;

    @RequestMapping(value = "/GitClone", method=RequestMethod.POST)
    public String GitClone(@RequestParam("URL") String url, @RequestParam("Name") String name, HttpSession session) throws Exception {
    	
    		Future<GitControl> git = gitControlService.GitClone(url, name);
    		session.setAttribute("Git", git);   		
    		return "analysis/cloning";
    }
    @RequestMapping(value = "/GitClone/IsDone.json", method=RequestMethod.GET)
    @ResponseBody 
    public String IsGitGloneDone(HttpSession session, HttpServletResponse response)
    {
    		
    		response.setContentType("application/json");
    		Gson gson = new Gson();
    		ResultData result = new ResultData(false);
    		try{
    		Future<GitControl> git = (Future<GitControl>) session.getAttribute("Git");
    		
    		if(git.isDone()){
    			result.setResult(true);
    		}
    		
    		}catch(Exception e){
    			return gson.toJson(result);
    		}
    		return gson.toJson(result);
    }
}
