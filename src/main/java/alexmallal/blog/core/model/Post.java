package alexmallal.blog.core.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import alexmallal.blog.core.commons.model.Base;

@Entity
@Table(name = "post")
public class Post extends Base {

	private String title;

	private String byline;

	private String author;

	private String imageUrl;

	private List<Category> categories;

	private Date dateCreated;

	private Date dateLastUpdated;
	
	private User singleUser;

	
	
	private String body;
	

	
	private String videoUrl;
	
	private Boolean topPost;
	private Boolean thumbnail;
	
	@NotEmpty
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getByline() {
		return byline;
	}
	public void setByline(String byline) {
		this.byline = byline;
	}
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinTable(name = "post_category", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}
	public void setDateLastUpdated(Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}
	
	@NotEmpty
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Size(min = 500)
	@Column(length=2000)
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@NotEmpty
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Boolean getTopPost() {
		return topPost;
	}
	public void setTopPost(Boolean topPost) {
		this.topPost = topPost;
	}
	public Boolean getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Boolean thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinTable(name = "post_user", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	public User getSingleUser() {
		return singleUser;
	}
	public void setSingleUser(User singleUser) {
		this.singleUser = singleUser;
	}


}
