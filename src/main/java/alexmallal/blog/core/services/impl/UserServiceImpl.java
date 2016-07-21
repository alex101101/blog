package alexmallal.blog.core.services.impl;


import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import alexmallal.blog.core.dao.RoleDao;
import alexmallal.blog.core.dao.UserDao;
import alexmallal.blog.core.model.Role;
import alexmallal.blog.core.model.User;
import alexmallal.blog.core.services.UserService;


@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
	public User findUserById(Long id){
		return userDao.findById(id);
	}
	
	public void deleteUser(User user){
		userDao.delete(user);
	}
	
	public long countAllUsers(){
		return userDao.countEntries();
	}
	
	public boolean checkUsernameAvailable(String name){
		return userDao.checkUsernameAvailable(name);
	}
	
	public User createUser(User user){
		//not using dto
		user.setDateOfRegister(new Date());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (roleDao.findByNameNot("ROLE_ADMIN") != null) {logger.debug("YAYYY");}
		
        user.setRoles(new HashSet<>(roleDao.findByNameNot("ROLE_ADMIN")));
        
		return userDao.save(user);
	}
	
	
	public User getUserName(String name){
		return userDao.getUserName(name);
	}
	
	public User updateUserPassword(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userDao.update(user);
	}
	
	public User updateUser(User user){
		return userDao.update(user);
	}
	
	public List<User> findAllUsers(){
		return userDao.findAll();
	}
	
	public List<User> findSubsetUsers(int firstResult, int maxResults){
		return userDao.findRangeEntries(firstResult, maxResults);
	}
	

	
}
