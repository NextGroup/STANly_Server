package stanly.server.DiffDevloper.Controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stanly.server.DiffDevloper.Service.DiffDevService;

@RequestMapping("/diff")
@Controller(value = "DiffDevController")
public class DiffDevController {
	protected static final Logger logger = Logger.getLogger("CommonViewController");
	
	@Autowired
	@Qualifier("DiffDevService")
	private DiffDevService diffService;
	
	@RequestMapping(value = "/dev", method=RequestMethod.GET)
	@ResponseBody
	public String getDiffDevList(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Diff - Dev List");
			return diffService.getDiffDev(name);
	}
}
