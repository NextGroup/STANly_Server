package stanly.server.ArchPersonalPage.Controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stanly.server.ArchPersonalPage.Service.ArchProjectService;

import com.google.gson.Gson;

@RequestMapping("/ArchProject")
@Controller(value = "ArchProjectController")
public class ArchProjectController {
	protected static Logger logger = Logger.getLogger("ArchProjectController");

	@Autowired
	private ArchProjectService archView;
	
	@RequestMapping(value = "/project", method=RequestMethod.GET)
	@ResponseBody
	public String getRelationSrc(@RequestParam("Name") String name,HttpServletResponse response) throws Exception {
			Gson gson = new Gson();
			logger.info("Start");
			return gson.toJson(archView.getCommitterProject(name));
	}
}
