package stanly.server.sample;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import stanly.server.sample.Data.Person;
import stanly.server.sample.Service.PersonService;
 
@RequestMapping("/sample")
@Controller(value = "helloController")
public class HelloController {
	
	@Resource(name="personService")
	private PersonService personService;
	
	protected static Logger logger = Logger.getLogger("controller");
	
    @RequestMapping(value = "/hello")
    public String hello(ModelMap model) throws Exception {
        return "hello";
    }
    
    
    @RequestMapping(value = "/persons" , method = RequestMethod.GET )
    public String getPersons(Model model)
    {
    		List<Person> persons = personService.getAll();
    		model.addAttribute("persons", persons);
    		return "personsPage";
    }
    
    @RequestMapping(value = "/persons/add", method = RequestMethod.GET)
    public String add(
    @RequestParam(value="firstname", required=true) String firstName,
    @RequestParam(value="lastname", required=true) String lastName,
    @RequestParam(value="money", required=true) Double money) {
     
    logger.debug("Received request to add new person");
     
    // Call PersonService to do the actual adding
    personService.add(firstName, lastName, money);
     
    // This will resolve to /WEB-INF/jsp/addedpage.jsp
    return "addedpage";
    }
    
    @RequestMapping(value = "/persons/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) Integer id, 
    Model model) {
     
    logger.debug("Received request to delete existing person");
     
    // Call PersonService to do the actual deleting
    personService.delete(id);
     
    // Add id reference to Model
    model.addAttribute("id", id);
     
    // This will resolve to /WEB-INF/jsp/deletedpage.jsp
    return "deletedpage";
    }
    
    @RequestMapping(value = "/persons/edit", method = RequestMethod.GET)
    public String edit(
    @RequestParam(value="id", required=true) Integer id,
    @RequestParam(value="firstname", required=true) String firstName,
    @RequestParam(value="lastname", required=true) String lastName,
    @RequestParam(value="money", required=true) Double money,
    Model model){
     
    logger.debug("Received request to edit existing person");
     
    // Call PersonService to do the actual editing
    personService.edit(id, firstName, lastName, money);
     
    // Add id reference to Model
    model.addAttribute("id", id);
     
    // This will resolve to /WEB-INF/jsp/editedpage.jsp
    return "editedpage";
    }
    
    
}