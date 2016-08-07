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
import alexmallal.blog.core.model.User;
import alexmallal.blog.core.services.BlogService;
import alexmallal.blog.core.services.CategoryService;
import alexmallal.blog.core.services.UserService;
import alexmallal.elasticsearch.services.BlogElasticService;


@RestController
public class BlogRestController {
	@Autowired
	BlogService blogService;
	
	@Autowired
	UserService userService;
	
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
	
	//for populating dropdown option list on page load, subcomponent
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

//	Need user authentication for put and delete.  Will now get the post creator's user 
//	object in each post, compare currentUser to post.getSingleUser for equivalence,
//	if true then update, otherwise send badrequest.  Use javascript to put edit buttons
//	only for user's own posts (insecure, but if overwritten still can't edit other's posts
	
	@RequestMapping(value = "rest/blog/{id}", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post, Principal principal) {
		final String currentUsername = principal.getName();
		if (post==null) {
			logger.debug("Post not found");
			return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
		}
		logger.debug("here it is"+post.getAuthor().toString());
		User currentUser = userService.getUserName(currentUsername);
		Post entirePost = blogService.findPostById(post.getId());
		User currentPostUserObject = (User) entirePost.getSingleUser();
		if (currentUser.getUsername().equals(currentPostUserObject.getUsername())) {
			logger.debug("Working true");
			entirePost.setId(post.getId());
			entirePost.setAuthor(post.getAuthor());
			entirePost.setBody(post.getBody());
			entirePost.setImageUrl(post.getImageUrl());
			entirePost.setVideoUrl(post.getVideoUrl());
			entirePost.setTitle(post.getTitle());
			entirePost.setThumbnail(post.getThumbnail());
			entirePost.setTopPost(post.getTopPost());
			entirePost.setDateLastUpdated(new Date());
			entirePost.setCategories(post.getCategories());
			blogService.updatePost(entirePost);
			return new ResponseEntity<Post>(entirePost, HttpStatus.OK);
		}
		logger.debug("You are not allowed to post to someone elses post");
		return new ResponseEntity<Post>(HttpStatus.UNAUTHORIZED);

	}
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

