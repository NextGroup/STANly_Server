package stanly.server.GitProject.Controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stanly.server.GitProject.Service.GitControlService;

@RequestMapping("/project")
@Controller(value = "projectController")
public class ProjectController {
	protected static Logger logger = Logger.getLogger("controller");
	
	@Autowired
	@Qualifier("gitControlService")
	private GitControlService gitControlService;

    @RequestMapping(value = "/GitClone", method=RequestMethod.POST)
    public String GitClone(HttpSession session) throws Exception {

  
    		
    		return "hello";
    }
}
