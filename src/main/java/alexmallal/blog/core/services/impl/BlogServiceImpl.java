package alexmallal.blog.core.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import alexmallal.blog.core.dao.CategoryDao;
import alexmallal.blog.core.dao.PostDao;
import alexmallal.blog.core.model.Category;
import alexmallal.blog.core.model.Post;
import alexmallal.blog.core.model.User;
import alexmallal.blog.core.services.BlogService;
import alexmallal.blog.core.services.UserService;

@Service("blogService")
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
	
	public Post findPostById(Long id) {
		return postDao.findById(id);
	}
	
	public void deletePost(Post post) {
		postDao.delete(post);
	}
	
	public long countAllPosts() {
		return postDao.countAllPosts();
	}
	
	public Post createPost(Post post) {
		post.setDateCreated(new Date());
		//Get current authenticated user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    
	    if(logger.isDebugEnabled()){
			logger.debug("RESULTS!!"+name);
		}
	    
		User singleUser = userService.getUserName(name);
		post.setSingleUser(singleUser);
		return postDao.save(post);
	}
	
	public Post updatePost(Post post) {
		return postDao.save(post);
	}
	
	public List<Post> findAllPosts() {
		return postDao.findAll();
	}
	
//	public List<Post> findAllPostsByCategory(String categoryName) {
//		return postDao.findByCategoryIn(categoryName);
//	}
	
	public Page<Post> findSubsetPosts(int firstResult, int maxResults) {
		PageRequest request =
	    new PageRequest(firstResult - 1, maxResults, Sort.Direction.DESC, "dateCreated");
		return postDao.findAll(request);
	}
	public List<Post> searchForPostsByTitle(String title) {
		return postDao.findByTitle(title);
	}

}
