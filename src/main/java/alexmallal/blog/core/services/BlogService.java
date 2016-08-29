package alexmallal.blog.core.services;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.data.domain.Page;

import alexmallal.blog.core.model.Category;
import alexmallal.blog.core.model.Post;

public interface BlogService {
//	void testSetAndGet();
	Post findPostById(Long id);
	void deletePost(Post post);
	long countAllPosts();
	Post createPost(Post post);
	Post updatePost(Post post);
	List<Post> findAllPosts();
//	List<Post> findAllPostsByCategory(Category category);
	Page<Post> findSubsetPosts(int firstResult, int maxResults);
	List<Post> searchForPostsByTitle(String title);
}
