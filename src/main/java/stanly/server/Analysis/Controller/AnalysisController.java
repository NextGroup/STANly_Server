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

/**
 * 분석과 관련된 컨트롤러 객체 clone받은 Git 프로젝트를 정적 분석하여 정보를 수집한다.
 * @author Karuana 
 */
/**
 * @author Karuana
 *
 */
@RequestMapping("/project")
@Controller(value = "analysisController")
public class AnalysisController {
	/**
	 * log4j의 logger출력을 위한 객체를 얻는다. 해당 객체로 log를 찍게 된다.
	 */
	protected static Logger logger = Logger.getLogger("Analysiscontroller");

	/**
	 *  분석과 관련된 서비스와 연결한다.
	 */
	@Autowired
	@Qualifier("analysisService")
	private AnalysisService analysis;

	/**
	 * 	프로젝트 정보를 컨트롤하는 서비스와 연결한다.
	 */
	@Autowired
	@Qualifier("projectinfoService")
	private ProjectInfoService projectService;
    
	/**
	 * 	LastCommit(날짜 순으로 가장 마지막에 커밋한 데이터를 기반으로 분석한다.
	 * @param session 프로젝트 명을 공유하기 위한 세션. gitClone을 받고 넘어와야하기 때문에 세션 유지가 필
	 * @param response 에러시 400 에러를 리턴하기 위한 응답 객체 
	 * @return analysis/analyzing 화면으로 이동한다.
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
	
	/**
	 *  Git 프로젝트 분석이 완료되었는지 확인한다. 
	 * @param session 비동기 작업이 완료되었는지 체크한다/
	 * @param response 응답의 타입을 지정한다. 
	 * @return 현재 분석이 완료되었는지를 알려주는 Json을 리턴한다. 
	 */
	@RequestMapping(value = "/Analysis/IsDone.json", method=RequestMethod.GET)
	@ResponseBody 
	public String IsGitGloneDone(HttpSession session, HttpServletResponse response)
	{
	    		
	    		response.setContentType("application/json");
	    		Gson gson = new Gson();
	    		String pName = (String)session.getAttribute("ProjectName");
	    		ResultData result = new ResultData(false,pName);
	    		try{
	    			//세션에 넣어둔 비동기 작업의 결과물을 가져와 작업이 완료되었는지 체크한다.
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
/**
 * 분석과 관련된 컨트롤러 객체 clone받은 Git 프로젝트를 정적 분석하여 정보를 수집한다.
 * @author Karuana 
 */
/**
 * @author Karuana
 *
 */
@/**
 * 분석과 관련된 컨트롤러 객체 clone받은 Git 프로젝트를 정적 분석하여 정보를 수집한다.
 * @author Karuana 
 */
/**
 * @author Karuana
 *
 */
@/**
 * 분석과 관련된 컨트롤러 객체 clone받은 Git 프로젝트를 정적 분석하여 정보를 수집한다.
 * @author Karuana 
 */
/**
 * @author Karuana
 *
 */
@/**
 * 분석과 관련된 컨트롤러 객체 clone받은 Git 프로젝트를 정적 분석하여 정보를 수집한다.
 * @author Karuana 
 */
/**
 * @author Karuana
 *
 */
@/**
 * 분석과 관련된 컨트롤러 객체 clone받은 Git 프로젝트를 정적 분석하여 정보를 수집한다.
 * @author Karuana 
 */
/**
 * @author Karuana
 *
 */
@/**
 * 분석과 관련된 컨트롤러 객체 clone받은 Git 프로젝트를 정적 분석하여 정보를 수집한다.
 * @author Karuana 
 */
/**
 * @author Karuana
 *
 */
@/**
 * 분석과 관련된 컨트롤러 객체 clone받은 Git 프로젝트를 정적 분석하여 정보를 수집한다.
 * @author Karuana 
 */
/**
 * @author Karuana
 *
 */
@/**
 * 분석과 관련된 컨트롤러 객체 clone받은 Git 프로젝트를 정적 분석하여 정보를 수집한다.
 * @author Karuana 
 */
/**
 * @author Karuana
 *
 */
@
