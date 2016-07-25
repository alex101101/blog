package alexmallal.blog.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import alexmallal.blog.core.model.Category;




public interface CategoryDao  extends JpaRepository<Category, Long> {
	Category findById(Long id);
	
	@Query("select count(e) from Category e")
	long countAllCategories();

}
