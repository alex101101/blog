package alexmallal.blog.core.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ind")
public class PostController {
	private static final Logger logger = Logger.getLogger(HomeController.class);
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editIndPost(@PathVariable("id") long id, Model model){
    	//logs debug message
		if(logger.isDebugEnabled()){
			logger.debug("Ind post is executed!");
		}
        return "editpost";
    }
    

}
