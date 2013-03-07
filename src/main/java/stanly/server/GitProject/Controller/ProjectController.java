package stanly.server.GitProject.Controller;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/project")
@Controller(value = "projectController")
public class ProjectController {
	protected static Logger logger = Logger.getLogger("controller");
	
    @RequestMapping(value = "/hello")
    public String hello(ModelMap model) throws Exception {

    		File file = new File("../GitData");

    		if(!file.exists()){
    			//없다면 생성
    			file.mkdirs(); 
    		}else{
    			
    		}
    		
    		return "hello";
    }
}
