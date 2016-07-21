package alexmallal.blog.core.services.impl;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import alexmallal.blog.core.services.SecurityService;
import alexmallal.blog.core.services.UserService;

@Service
public class SecurityServiceImpl implements SecurityService{
    @Autowired
    private AuthenticationManager authManager;

    
	@Autowired
	UserService userService;


    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }

        return null;
    }
    
    @Override
    public boolean autoLogin( String username, String password, HttpServletRequest request) {
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
 
        Authentication authentication = authManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication );
 
        //put spring security login in session
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.getSession().setAttribute("loggedInUser", authUser.getUsername());
        logger.debug(String.format("Auto login %s successfully!", username));
        
        alexmallal.blog.core.model.User user = userService.getUserName(authUser.getUsername());
        user.setLastLoggedIn(new Date());
        this.userService.updateUser(user);
        
        return true;
    }
}