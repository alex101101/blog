package alexmallal.blog.core.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import alexmallal.blog.core.model.User;
import alexmallal.blog.core.services.SecurityService;
import alexmallal.blog.core.services.UserService;
import alexmallal.blog.core.services.impl.UserDetailsServiceImpl;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;
    
    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
	    
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logoutSuccess", required = false) String logoutSuccess
			) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		

		if (logoutSuccess != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}

		return model;

	}
	
	
//	@RequestMapping(value="/logout", method=RequestMethod.GET)
//	public String logoutPage(HttpServletRequest request, HttpServletResponse response){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){    
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//		return "redirect:http://localhost:8080/blog/login?logoutSuccess";
//		
//	}
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public ModelAndView userAccess(){
		ModelAndView model = new ModelAndView();
		model.setViewName("user");
		return model;
	}
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public String register(Model model){
		//add the class object to the form. spring:bind in jsp to map to individual fields
		model.addAttribute("userForm", new User());
		
		return "register";
	}
	
	 @RequestMapping(value = "register", method = RequestMethod.POST)
	    public String register(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model,  HttpServletRequest request) {

	        if (bindingResult.hasErrors()) {
	            return "register";
	        }
	        logger.debug("PASSWORD IS: " + userForm.getPassword());
	        //Temporarily save password for autologin before it is hashed:
	        String tempPasswordStore = userForm.getPassword();
	        //model attribute userform is taken back as a new User object.  Call the service
	        //to create new user in the database
	        userService.createUser(userForm);
	        
	        //login automatically without using the login form
	        securityService.autoLogin(userForm.getUsername(), tempPasswordStore, request);
	        tempPasswordStore = null;
	        return "redirect:/";
	    }
	
}
