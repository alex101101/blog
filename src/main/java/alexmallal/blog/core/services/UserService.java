package alexmallal.blog.core.services;


import java.util.List;

import alexmallal.blog.core.model.User;



public interface UserService {
	User findUserById(Long id);
	void deleteUser(User user);
	long countAllUsers();
	boolean checkUsernameAvailable(String name);
	User createUser(User user);
	User getUserName(String name);
	User updateUser(User user);
	User updateUserPassword(User user);
	List<User> findAllUsers();
	List<User> findSubsetUsers(int firstResult, int maxResults);
	
}