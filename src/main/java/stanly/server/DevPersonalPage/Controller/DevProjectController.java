package stanly.server.DevPersonalPage.Controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stanly.server.DevPersonalPage.Service.DevProjectServie;

import com.google.gson.Gson;

@RequestMapping("/DevProject")
@Controller(value = "DevProjectController")
public class DevProjectController {
	protected static Logger logger = Logger.getLogger("DevProjectController");

	@Autowired
	private DevProjectServie DevProject;
	
	@RequestMapping(value = "/project", method=RequestMethod.GET)
	@ResponseBody
	public String getRelationSrc(@RequestParam("Name") String name,HttpServletResponse response) throws Exception {
			Gson gson = new Gson();
			logger.info("Start");
			return gson.toJson(DevProject.getCommitterProject(name));
	}
	@RequestMapping(value = "/projectH", method=RequestMethod.GET)
	@ResponseBody
	public String getHmode(HttpServletResponse response) throws Exception {
			Gson gson = new Gson();
			logger.info("Start");
			return gson.toJson(DevProject.getHModeService());
	}
}
