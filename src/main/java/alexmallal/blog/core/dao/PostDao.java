package alexmallal.blog.core.dao;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import alexmallal.blog.core.model.Category;
import alexmallal.blog.core.model.Post;


public interface PostDao  extends JpaRepository<Post, Long> {
	@QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true"),
    	@QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL")
    })
	Post findById(Long id);
	
	@Query("select count(e) from Post e")
	long countAllPosts();

//	List<Post> findByCategoryIn(String categoryName);

	List<Post> findByTitle(String title);
	
//	query.setHint("org.hibernate.cacheable", true);
//    query.setHint("org.hibernate.cacheMode", "NORMAL");
    
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true"),
    	@QueryHint(name = "org.hibernate.cacheMode", value = "NORMAL")
    })
	List<Post> findAll();
}