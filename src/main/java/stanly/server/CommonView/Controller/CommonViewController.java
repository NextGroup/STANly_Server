package stanly.server.CommonView.Controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stanly.server.CommonView.Service.CommonService;

@RequestMapping("/common")
@Controller(value = "CommonViewController")
public class CommonViewController {
	protected static final Logger logger = Logger.getLogger("CommonViewController");
	
	@Autowired
	@Qualifier("commonService")
	private CommonService commonS;
	
	@RequestMapping(value = "/risk", method=RequestMethod.GET)
	@ResponseBody
	public String getProjectTree(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Critical Risk Getter");
			return commonS.getCriticalRisk(name);
	}
}
