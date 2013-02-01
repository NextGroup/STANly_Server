package stanly.server.sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
 
@RequestMapping("/sample")
@Controller(value = "helloController")
public class HelloController {
    @RequestMapping(value = "/hello")
    public String hello(ModelMap model) throws Exception {
        return "hello";
    }
}