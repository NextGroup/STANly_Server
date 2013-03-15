package stanly.server.Analysis.Controller;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import stanly.server.Analysis.Service.AnalysisService;
import stanly.server.GitProject.Git.GitControl;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.GitProject.Service.ProjectInfoService;
import stanly.server.GitProject.json.ResultData;

@RequestMapping("/project")
@Controller(value = "analysisController")
public class AnalysisController {
	protected static Logger logger = Logger.getLogger("Analysiscontroller");

	@Autowired
	@Qualifier("analysisService")
	private AnalysisService analysis;

	@Autowired
	@Qualifier("projectinfoService")
	private ProjectInfoService projectService;
    
	/**
	 * 	LastCommit을 기반으로 분석한다.
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/Analysis", method=RequestMethod.POST)
    public String AnalysisProject(HttpSession session, HttpServletResponse response)
    {
		try{
			String projectName = (String) session.getAttribute("ProjectName");
    			ProjectInfo info =projectService.getProjectInfo(projectName) ;
    			ProjectCommit commitId = projectService.getLastCommit(info);
    			Future<String> RootName = analysis.analysisNode(commitId);
    			session.setAttribute("Analysis", RootName);
    		
		}catch(Exception e)
		{
			logger.error(e);
			response.setStatus(400);
		}
		
		return "analysis/analyzing";
    }
	@RequestMapping(value = "/Analysis/IsDone.json", method=RequestMethod.GET)
	@ResponseBody 
	public String IsGitGloneDone(HttpSession session, HttpServletResponse response)
	{
	    		
	    		response.setContentType("application/json");
	    		Gson gson = new Gson();
	    		String pName = (String)session.getAttribute("ProjectName");
	    		ResultData result = new ResultData(false,pName);
	    		try{
	    			
	    			Future<String> rootName = (Future<String>) session.getAttribute("Analysis");
	    		
	    		if(rootName.isDone()){
	    			result.setResult(true);
	    			session.removeAttribute("Analysis");
	    		}
	    		
	    		}catch(Exception e){
	    			return gson.toJson(result);
	    		}
	    		return gson.toJson(result);
	 }
}
