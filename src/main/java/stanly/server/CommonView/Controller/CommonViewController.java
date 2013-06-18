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
	public String getCriticalRiskJson(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Critical Risk Getter");
			return commonS.getCriticalRisk(name);
	}
	
	@RequestMapping(value = "/pollution/rate", method=RequestMethod.GET)
	@ResponseBody
	public String getPollutionList(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Critical Risk Getter");
			return commonS.getPollutionRisk(name);
	}
	
	@RequestMapping(value = "/pollution/fat", method=RequestMethod.GET)
	@ResponseBody
	public String getFatList(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Critical Risk Getter");
			return commonS.getFatRatioRank(name);
	}
	@RequestMapping(value = "/pollution/cprate", method=RequestMethod.GET)
	@ResponseBody
	public String getCpList(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Critical Risk Getter");
			return commonS.getCpRatioRank(name);
	}
	@RequestMapping(value = "/pollution/coupling", method=RequestMethod.GET)
	@ResponseBody
	public String getCouplingList(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
			logger.info("Critical Risk Getter");
			return commonS.getCouplingRatioRank(name);
	}
	@RequestMapping(value = "/pollution/static", method=RequestMethod.GET)
	@ResponseBody
	public String getStaticAnalysis(@RequestParam("name") String name,@RequestParam("mode") int mode,  HttpServletResponse response) throws Exception {
			logger.info("Critical Risk Getter");
			return commonS.getSAList(name,mode);
	}
	
}
