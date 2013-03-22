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

/**
 * @author Karuana
 * Metric에 관련된 그래프나 차트를 그리기 위한 JSON을 반환하는 컨트롤러 객체이다. 
 */
@RequestMapping("/component")
@Controller(value = "metricComponentController")
public class MetricComponentController {

	protected static final Logger logger = Logger.getLogger("MetricComponent - Controller");
	
	@Autowired
	@Qualifier("metricViewService")
	private MetricViewService metricView;

	/**
	 * project Tree를 리턴한다. 
	 * @param name
	 * @param nsLeft
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ProjectTree", method=RequestMethod.GET)
	@ResponseBody
	public String getProjectTree(@RequestParam("Name") String name,@RequestParam("nodeID") int nsLeft,HttpServletResponse response) throws Exception {
			logger.info("Get Project Tree node ID- "+ nsLeft );
			return metricView.getTreeNode(name, nsLeft);
	}
	
	/**
	 * CodeSize View를 리턴한다.
	 * 노드 아이디는 패키지 이상을 넘겨줘야 한다.
	 * @param name
	 * @param nsLeft
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/CodeSize", method=RequestMethod.GET)
	@ResponseBody
	public String getCodeSize(@RequestParam("Name") String name,@RequestParam("nodeID") int nsLeft,HttpServletResponse response) throws Exception {
			logger.info("Get Project CodeSize node ID- "+ nsLeft );
			return metricView.getCodeSize(name, nsLeft);
	}

	/**
	 * 마틴 디스턴스 그래프를 그리기 위한 정보를 리턴한다.
	 * 노드 아이디는 패키지 이상을 넘겨줘야 한다.
	 * @param name
	 * @param nsLeft
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/MartinDistance", method=RequestMethod.GET)
	@ResponseBody
	public String getMartinDistance(@RequestParam("Name") String name,@RequestParam("nodeID") int nsLeft,HttpServletResponse response) throws Exception {
			logger.info("Get Project Tree node ID- "+ nsLeft );
			return metricView.getMartinDistance(name, nsLeft);
	}
	
	/**
	 * Metric 정보를 리턴한다. 
	 * @param name
	 * @param nsLeft
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Metrics", method=RequestMethod.GET)
	@ResponseBody
	public String getMetrics(@RequestParam("Name") String name,@RequestParam("nodeID") int nsLeft,HttpServletResponse response) throws Exception {
			logger.info("Get Project Tree node ID- "+ nsLeft );
			return metricView.getMetrics(name, nsLeft);
	}
	
	/**
	 * 오염도 그래프를 그리기 위한 데이터를 받는다. 
	 * @param name
	 * @param nsLeft
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/PollutionView", method=RequestMethod.GET)
	@ResponseBody
	public String getPollutionView(@RequestParam("Name") String name,@RequestParam("nodeID") int nsLeft,HttpServletResponse response) throws Exception {
		logger.info("Get PollutionView node ID- "+ nsLeft );
		return metricView.getPollutionChart(name, nsLeft);
	}
	
	/**
	 * 패키지 관계도 그래프를 그리기 위한 정보를 리턴받는다. 
	 * @param name
	 * @param nsLeft
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/CompositionView", method=RequestMethod.GET)
	@ResponseBody
	public String getCompositionView(@RequestParam("Name") String name,@RequestParam("nodeID") int nsLeft,HttpServletResponse response) throws Exception {
		logger.info("Get CompositionView node ID- "+ nsLeft );
		return metricView.getCompositionView(name, nsLeft);
	}
	
	
}
