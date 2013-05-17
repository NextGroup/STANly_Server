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

/**
 * @author Karuana
 *	프로젝트에 관련된 기능들에 연결해주는 Controller이다. 
 */
@RequestMapping("/project")
@Controller(value = "projectController")
public class ProjectController {
	protected static Logger logger = Logger.getLogger("projectcontroller");
	
	/**
	 * Git URL 정보를 기반으로 Project 명을 생성한다. 
	 * @param URL
	 * @return
	 */
	private String getProjectName(String URL)
	{
		  String[]  arr =  URL.split("/");
		  String[] Name =   arr[arr.length-1].split("\\.");
		  logger.info(Name[0]);
		return Name[0];
	}
	/**
	 * Git과 관련된 서비스를 연결한다.
	 */
	@Autowired
	@Qualifier("gitControlService")
	private GitControlService gitControlService;
	
	/**
	 * 프로젝트와 관련된 작업을 처리하는 서비스와 연결한다. 
	 */
	@Autowired
	@Qualifier("projectinfoService")
	private ProjectInfoService projectService;

    /**
     * 주어진 Git URL을 바탕으로 서버에 Clone받는다. 
     * @param url GitURL을 제공한다. 마지막에 .git으로 끝나야 한다. Http 방식으로 처리 (필수 요소)
     * @param name 프로젝트 명을 입력한다(선택)
     * @param session 비동기 처리를 위한 세션 
     * @return  "analysis/cloning" 페이지로 넘어간다.
     * @throws Exception
     */
    @RequestMapping(value = "/GitClone", method=RequestMethod.POST)
    public String GitClone(@RequestParam("URL") String url,@RequestParam(value="Name" , required=false) String name, HttpSession session) throws Exception {
    		logger.info(url);
    		String ProjectName = (name!=null) ? name: getProjectName(url);
    		Future<GitControl> git = gitControlService.GitClone(url,ProjectName );
    		session.setAttribute("Git", git);
    		session.setAttribute("ProjectName", ProjectName);
    		return "analysis/cloning";
    }
    
    /**
     * 주어진 정보에 해당하는 프로젝트가 이미 존재하는지 체크한다. 
     * @param url GitURL (필수)
     * @param name 프로젝트 명 (선택)
     * @param response 응답 코드를 리턴하기 위한 객체 
     * @return Json 타입의 데이터 
     */
    @RequestMapping(value = "/IsProject", method=RequestMethod.GET)
    @ResponseBody
    public String IsProject(@RequestParam("URL") String url,@RequestParam(value="Name" , required=false) String name,HttpServletResponse response)
    	{
    			response.setContentType("application/json");
    		logger.info("input url = " + url);
    		String ProjectName = (name!=null) ? name: getProjectName(url);

    		ProjectStateJSON	 state = new ProjectStateJSON(projectService.getProjectState(url, ProjectName).name());
    		Gson gson = new Gson();
    		
    		return gson.toJson(state);
    	}
    
    /**
     * Git의 Clone이 전부 다되었는지 체크한다. 
     * @param session 비동기 체크를 위한 세션 
     * @param response
     * @return Json 데이터 
     */
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
