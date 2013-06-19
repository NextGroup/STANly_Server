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
/**
 * @author Karuana
 * Metric 정보를 표시하기 위한 List Componet 정보를 가져온다, 
 */
@RequestMapping("/component")
@Controller(value = "metricListController")
public class MetricListController {
	
	protected static final Logger logger = Logger.getLogger("MetricComponent - Controller");
	
	@Autowired
	@Qualifier("metricViewService")
	private MetricViewService metricView;
	
	
	
	@RequestMapping(value = "/composition/rlist", method=RequestMethod.GET)
	@ResponseBody
	public String getRelation(@RequestParam("name") String name,@RequestParam("src") int SrcID,@RequestParam("tar") int TarID,HttpServletResponse response) throws Exception {
			logger.info("relation"+ SrcID );
			return metricView.getRelation(name, SrcID,TarID);
	}
	
	/**
	 * 릴레이션 정보들을 가져온다. 
	 * @param name
	 * @param SrcID
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/RelationList", method=RequestMethod.GET)
	@ResponseBody
	public String getRelationSrc(@RequestParam("Name") String name,@RequestParam("SrcID") int SrcID,HttpServletResponse response) throws Exception {
			logger.info("Get Project Relation node ID- "+ SrcID );
			return metricView.getRelationWithSrc(name, SrcID);
	}
	/**
	 * 오염도 정보를 가져온다.
	 * @param name
	 * @param SrcID
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/PollutionList", method=RequestMethod.GET)
	@ResponseBody
	public String getPollutionList(@RequestParam("Name") String name,@RequestParam("SrcID") int SrcID,HttpServletResponse response) throws Exception {
			logger.info("Get Project PollutionList node ID- "+ SrcID );
			return metricView.getPollutionList(name, SrcID);
	}
	
	/**
	 * 마틴 정보를 표시하기 위한 JSON을 가져온다. 
	 * @param name
	 * @param SrcID
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/MartinList", method=RequestMethod.GET)
	@ResponseBody
	public String getMartinList(@RequestParam("Name") String name,@RequestParam("SrcID") int SrcID,HttpServletResponse response) throws Exception {
			logger.info("Get Project MartinList node ID- "+ SrcID );
			return metricView.getMartinList (name, SrcID);
	}
}
