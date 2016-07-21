package alexmallal.blog.core.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import alexmallal.blog.core.commons.model.Base;

@Entity
@Table(name = "category")
public class Category extends Base {
	private String categoryName;
    
    private Set<Post> posts;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "categories")
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
    
    

}
