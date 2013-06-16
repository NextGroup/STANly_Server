package stanly.server.PollutionView.Controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stanly.server.PollutionView.Service.PollutionViewService;

@RequestMapping("/pollution")
@Controller(value = "PollutionViewController")
public class PollutionViewController {
	protected static final Logger logger = Logger.getLogger("PollutionViewController");
	
	@Autowired
	@Qualifier("PollutionViewService")
	private PollutionViewService PVService;

	@RequestMapping(value = "/fat", method=RequestMethod.GET)
	@ResponseBody
	public String getFAT(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Fat");
			return PVService.getFatList(name);
	}
	
	@RequestMapping(value = "/coupling", method=RequestMethod.GET)
	@ResponseBody
	public String getCoupling(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Fat");
			return PVService.getCouplingList(name);
	}
	
	@RequestMapping(value = "/cprate", method=RequestMethod.GET)
	@ResponseBody
	public String getCPRate(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Fat");
			return PVService.getCPList(name);
	}
	@RequestMapping(value = "/static", method=RequestMethod.GET)
	@ResponseBody
	public String getStatic(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Fat");
			return PVService.getSAList(name);
	}
	
	@RequestMapping(value = "/basiclist", method=RequestMethod.GET)
	@ResponseBody
	public String getStaticBasicList(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("basic");
			return PVService.getTotalSAListBasic(name);
	}
}
