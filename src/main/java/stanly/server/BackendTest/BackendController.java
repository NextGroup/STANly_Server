package stanly.server.BackendTest;

import java.util.concurrent.Future;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/backend")
@Controller(value = "BackendController")
public class BackendController {

		@Autowired
		@Qualifier("backendService")
		private BackendService bservice;

		@RequestMapping(value="/BackendTest", method=RequestMethod.GET)  
		public String GetBackendData(HttpSession session)
		{
			session.setAttribute("Datas", bservice.add("Test"));
			
			return "TestBackend";
		}
		@RequestMapping("/reportstatus.json")  
		@ResponseBody 
		public String reportStatus(HttpSession session) {  
		    Future<String> report = (Future<String>)session.getAttribute("Datas");  
		      
		    if(report.isDone()) {  
		        System.out.println("Report Generation Done");  
		        return "COMPLETE";  
		    }  
		    else {  
		        System.out.println("Still Working on Report");  
		        return "WORKING";  
		    }  
		}
}
