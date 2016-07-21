package alexmallal.blog.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import alexmallal.blog.core.model.Category;
import alexmallal.blog.core.model.Post;


public interface PostDao  extends JpaRepository<Post, Long> {
	Post findById(Long id);
	
	@Query("select count(e) from Post e")
	long countAllPosts();

//	List<Post> findByCategoryIn(String categoryName);

	List<Post> findByTitle(String title);
}