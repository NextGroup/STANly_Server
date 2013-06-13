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

import stanly.server.CommonView.Service.LinkedDService;

@RequestMapping("/linkdev")
@Controller(value = "LinkedDevController")
public class LinkedDevController {
	protected static final Logger logger = Logger.getLogger("LinkedDevController");

	@Autowired
	@Qualifier("LinkedDService")
	private LinkedDService LinkedS;
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	@ResponseBody
	public String getLinkedDEV(@RequestParam("name") String name, @RequestParam("nsleft") int NSleft, HttpServletResponse response) throws Exception {
			logger.info("Critical getLinkedDEV Getter");
			return LinkedS.getLinkedDevList(name, NSleft);
	}
	
}
