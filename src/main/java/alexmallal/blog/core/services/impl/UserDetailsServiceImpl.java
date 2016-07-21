package alexmallal.blog.core.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import alexmallal.blog.core.dao.UserDao;
import alexmallal.blog.core.model.Role;
import alexmallal.blog.core.model.User;
import alexmallal.blog.core.web.HomeController;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserDao userDao;

    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserName(username);
        if(user!=null){
        	if(logger.isDebugEnabled()){
    			logger.debug("found user!"+user.getUsername());
    		}
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        //hard code hack. add API role to user: alexmallal
//        if (username=="alexmallal") {
//        	grantedAuthorities.add(new SimpleGrantedAuthority("api"));
//        }
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}