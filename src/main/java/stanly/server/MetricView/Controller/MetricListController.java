package stanly.server.MetricView.Controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stanly.server.MetricView.Service.MetricViewService;
@RequestMapping("/component")
@Controller(value = "metricListController")
public class MetricListController {
	
	protected static final Logger logger = Logger.getLogger("MetricComponent - Controller");
	
	@Autowired
	@Qualifier("metricViewService")
	private MetricViewService metricView;
	
	@RequestMapping(value = "/Relation", method=RequestMethod.GET)
	@ResponseBody
	public String getRelationSrc(@RequestParam("Name") String name,@RequestParam("SrcName") String SrcName,HttpServletResponse response) throws Exception {
			logger.info("Get Project Relation node ID- "+ SrcName );
			
			return metricView.getRelationWithSrc(name, SrcName);
	}
	
}
