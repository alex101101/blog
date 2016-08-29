package alexmallal.blog.core.web;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
	private static final Logger logger = Logger.getLogger(HomeController.class);
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(Model model){
    	//logs debug message
		if(logger.isDebugEnabled()){
			logger.debug("getWelcome is executed!");
		}
        return "home";
    }
    
    
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String makePost(Model model){
    	//logs debug message
		if(logger.isDebugEnabled()){
			logger.debug("Creat Post is executed!");
		}
        return "post";
    }
    
    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public String makeIndPost(@PathVariable("id") long id, Model model){
    	//logs debug message
		if(logger.isDebugEnabled()){
			logger.debug("Ind post is executed!");
		}
        return "postind";
    }
    
//    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
//    public String editIndPost(@PathVariable("id") long id, Model model){
//    	//logs debug message
//		if(logger.isDebugEnabled()){
//			logger.debug("Ind post is executed!");
//		}
//        return "editpost";
//    }
    

}
