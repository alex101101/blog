package alexmallal.blog.core.web;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import alexmallal.blog.core.model.User;
import alexmallal.blog.core.services.UserService;


@RestController
public class UserRestController {
	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(UserRestController.class);
	
	
	@RequestMapping(value = "rest/accountdetails/", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<User> getUser(Principal principal) {
		final String currentUsername = principal.getName();

			User currentUser = userService.getUserName(currentUsername);
			if (currentUser==null) {
				logger.debug("Username not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<User>(currentUser, HttpStatus.OK);

	}
	
	@RequestMapping(value = "rest/accountdetails/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {


			if (!userService.checkUsernameAvailable(user.getUsername())) {
				logger.debug("Username not available");
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
			userService.createUser(user);
			HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/rest/accountdetails/").build().toUri());
	        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}
	

	

	@RequestMapping(value = "rest/accountdetails/all", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser(Principal principal) {


		List<User> users = userService.findAllUsers();
			if (users==null) {
				logger.debug("Unsuccessful find all");
				return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}
	
	@RequestMapping(value = "rest/accountdetails/", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestBody User user, Principal principal) {
		final String currentUsername = principal.getName();
		User currentUser = userService.getUserName(currentUsername);
		if (user==null) {
			logger.debug("Username not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		currentUser.setEmailAddress(user.getEmailAddress());
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		if(user.getPassword()!=null) {
		currentUser.setPassword(user.getPassword());
		userService.updateUserPassword(currentUser);
		}
		userService.updateUser(currentUser);
		
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "rest/accountdetails/", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(Principal principal) {
		final String currentUsername = principal.getName();

			User currentUser = userService.getUserName(currentUsername);
			if (currentUser==null) {
				logger.debug("Username not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			userService.deleteUser(currentUser);
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);

	}

}
