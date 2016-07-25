package alexmallal.blog.core.web;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import alexmallal.blog.core.model.Category;
import alexmallal.blog.core.model.Post;
import alexmallal.blog.core.services.BlogService;
import alexmallal.blog.core.services.CategoryService;


@RestController
public class BlogRestController {
	@Autowired
	BlogService blogService;
	
	@Autowired
	CategoryService categoryService;
	
	private static final Logger logger = Logger.getLogger(UserRestController.class);
	
	
	@RequestMapping(value = "rest/blog/", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Post>> getAllEntries() {

			List<Post> blogList = blogService.findAllPosts();
			if (blogList==null) {
				logger.debug("No posts found");
				return new ResponseEntity<List<Post>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Post>>(blogList, HttpStatus.OK);

	}
	
	@RequestMapping(value = "rest/blog/{id}", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Post> getPost(@PathVariable("id") long id) {

			Post singlePost = blogService.findPostById(id);
			if (singlePost==null) {
				logger.debug("No post found for this id");
				return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Post>(singlePost, HttpStatus.OK);

	}
	
	@RequestMapping(value = "rest/blog/categories", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllCategories() {

			List<Category> categoryList = categoryService.findAllCategories();
			if (categoryList==null) {
				logger.debug("No categories found");
				return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "rest/blog/", method = RequestMethod.POST)
	public ResponseEntity<Post> createUser(@Valid @RequestBody Post post, UriComponentsBuilder ucBuilder) {
			//for test purposes
			post.setDateCreated(new Date());
			post.setDateLastUpdated(new Date());
			blogService.createPost(post);
			HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/rest/blog/{id}").buildAndExpand(post.getId()).toUri());
	        return new ResponseEntity<Post>(post, headers, HttpStatus.CREATED);

	}
//	
//
//	
//
//	@RequestMapping(value = "rest/accountdetails/all", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//	public ResponseEntity<List<User>> getAllUser(Principal principal) {
//
//
//		List<User> users = userService.findAllUsers();
//			if (users==null) {
//				logger.debug("Unsuccessful find all");
//				return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
//			}
//			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
//
//	}
//	
//	@RequestMapping(value = "rest/accountdetails/", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
//	public ResponseEntity<User> updateUser(@RequestBody User user, Principal principal) {
//		final String currentUsername = principal.getName();
//		User currentUser = userService.getUserName(currentUsername);
//		if (user==null) {
//			logger.debug("Username not found");
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//		}
//		currentUser.setEmailAddress(user.getEmailAddress());
//		currentUser.setFirstName(user.getFirstName());
//		currentUser.setLastName(user.getLastName());
//		currentUser.setPassword(user.getPassword());
//		userService.updateUser(currentUser);
//		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
//	}
//	
//	@RequestMapping(value = "rest/accountdetails/", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
//	public ResponseEntity<User> deleteUser(Principal principal) {
//		final String currentUsername = principal.getName();
//
//			User currentUser = userService.getUserName(currentUsername);
//			if (currentUser==null) {
//				logger.debug("Username not found");
//				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//			}
//			userService.deleteUser(currentUser);
//			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//
//	}

}

