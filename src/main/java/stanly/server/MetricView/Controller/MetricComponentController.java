package stanly.server.MetricView.Controller;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stanly.server.GitProject.Git.GitControl;
import stanly.server.MetricView.Service.MetricViewService;

@RequestMapping("/component")
@Controller(value = "metricComponentController")
public class MetricComponentController {

	protected static final Logger logger = Logger.getLogger("MetricComponent - Controller");
	
	@Autowired
	@Qualifier("metricViewService")
	private MetricViewService metricView;

	@RequestMapping(value = "/ProjectTree", method=RequestMethod.GET)
	@ResponseBody
	public String getProjectTree(@RequestParam("Name") String name,@RequestParam("nodeID") int nsLeft,HttpServletResponse response) throws Exception {
			logger.info("Get Project Tree node ID- "+ nsLeft );
			return metricView.getTreeNode(name, nsLeft);
	}

	@RequestMapping(value = "/MartinDistance", method=RequestMethod.GET)
	@ResponseBody
	public String getMartinDistance(@RequestParam("Name") String name,@RequestParam("nodeID") int nsLeft,HttpServletResponse response) throws Exception {
			logger.info("Get Project Tree node ID- "+ nsLeft );
			return metricView.getMartinDistance(name, nsLeft);
	}
}
