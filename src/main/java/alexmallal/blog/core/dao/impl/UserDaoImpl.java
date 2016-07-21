package alexmallal.blog.core.dao.impl;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import alexmallal.blog.core.commons.dao.impl.GenericDaoImpl;
import alexmallal.blog.core.dao.UserDao;
import alexmallal.blog.core.model.User;


@Repository
@Transactional
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	
	@Transactional
	public User getUserName(String userName) {
		Assert.notNull(userName);
		
		User user = null;
		
		Query query = entityManager.createQuery("select u from " + persistentClass.getSimpleName()
				+ " u where u.username = :username").setParameter("username", userName);
		
		try {
			user = (User) query.getSingleResult();
		} catch(NoResultException e) {
			//do nothing
		}
		
		return user;
	}
	
	@Transactional
	public boolean checkUsernameAvailable(String name){
		Assert.notNull(name);
		
		Query query = entityManager
				.createQuery("select count(*) from " + persistentClass.getSimpleName() 
					+ " u where u.username = :username").setParameter("username", name);
		
		Long count = (Long) query.getSingleResult();
		
		return count < 1;
	}
}
