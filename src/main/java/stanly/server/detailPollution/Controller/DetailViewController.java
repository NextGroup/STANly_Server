package stanly.server.detailPollution.Controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import stanly.server.detailPollution.Service.DetailViewService;

@RequestMapping("/detail")
@Controller(value = "DetailViewController")
public class DetailViewController {
	protected static final Logger logger = Logger.getLogger("DetailViewController");
	
	@Autowired
	@Qualifier("DetailViewService")
	private DetailViewService detailView;
	
	@RequestMapping(value = "/common", method=RequestMethod.GET)
	@ResponseBody
	public String getCommonDetail(@RequestParam("name") String name,@RequestParam("nsleft") int SrcID,HttpServletResponse response) throws Exception {
			logger.info("Get Detail Common View - "+ SrcID );
			return detailView.getCommonDetail(name, SrcID);
	}
	
	@RequestMapping(value = "/fat", method=RequestMethod.GET)
	@ResponseBody
	public String getFatDetail(@RequestParam("name") String name,@RequestParam("nsleft") int SrcID,HttpServletResponse response) throws Exception {
			logger.info("Get Detail Common View - "+ SrcID );
			return detailView.getFatDetailView(name, SrcID);
	}
	@RequestMapping(value = "/source", method=RequestMethod.GET)
	@ResponseBody
	public String getSourceDetail(@RequestParam("name") String name,@RequestParam("nsleft") int SrcID,HttpServletResponse response) throws Exception {
			logger.info("Get Source code - "+ SrcID );
			return detailView.getSourceFile(name, SrcID);
	}
	@RequestMapping(value = "/static", method=RequestMethod.GET)
	@ResponseBody
	public String getStaticAnalysisDetail(@RequestParam("name") String name,@RequestParam("nsleft") int SrcID,@RequestParam("type") int type, HttpServletResponse response) throws Exception {
			logger.info("Get Source code - "+ SrcID );
			return detailView.getStaticAnalysisDetail(name, SrcID,type);
	}

	@RequestMapping(value = "/chain", method=RequestMethod.GET)
	@ResponseBody
	public String getChainDetail(@RequestParam("name") String name,@RequestParam("nsleft") int SrcID, HttpServletResponse response) throws Exception {
			logger.info("Chain P - "+ SrcID );
			return detailView.getCPDetailView(name, SrcID);
	}
	
	@RequestMapping(value = "/coupling", method=RequestMethod.GET)
	@ResponseBody
	public String getCoupling(@RequestParam("name") String name,@RequestParam("nsleft") int SrcID, HttpServletResponse response) throws Exception {
			logger.info("coupling P - "+ SrcID );
			return detailView.getCouplingDetailView(name, SrcID);
	}
	
	@RequestMapping(value = "/composition", method=RequestMethod.GET)
	@ResponseBody
	public String getComposition(@RequestParam("name") String name,@RequestParam("nsleft") int SrcID, HttpServletResponse response) throws Exception {
			logger.info("composition P - "+ SrcID );
			return detailView.getMetricView(name, SrcID);
	}
}
