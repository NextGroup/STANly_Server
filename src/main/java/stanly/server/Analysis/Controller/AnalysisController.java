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
import stanly.server.GitProject.json.ResultData;

@RequestMapping("/project")
@Controller(value = "analysisController")
public class AnalysisController {
	protected static Logger logger = Logger.getLogger("Analysiscontroller");

	@Autowired
	@Qualifier("analysisService")
	private AnalysisService analysis;
	
    @RequestMapping(value = "/Analysis", method=RequestMethod.POST)
    public String AnalysisProject(HttpSession session, HttpServletResponse response)
    {
    		
    		
    		return null;
    }
}
