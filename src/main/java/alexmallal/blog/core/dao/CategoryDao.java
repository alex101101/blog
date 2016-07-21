package alexmallal.blog.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import alexmallal.blog.core.model.Category;


public interface CategoryDao  extends JpaRepository<Category, Long> {

}
