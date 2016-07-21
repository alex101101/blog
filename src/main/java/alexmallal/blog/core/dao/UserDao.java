package alexmallal.blog.core.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import alexmallal.blog.core.commons.dao.GenericDao;
import alexmallal.blog.core.model.User;



public interface UserDao extends GenericDao<User, Long> {
	
	public User getUserName(String userName);
	
	public boolean checkUsernameAvailable(String name);
	
}
