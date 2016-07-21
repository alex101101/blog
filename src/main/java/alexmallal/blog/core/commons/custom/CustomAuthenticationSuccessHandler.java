package alexmallal.blog.core.commons.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import alexmallal.blog.core.dao.UserDao;
import alexmallal.blog.core.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
 
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        HttpSession session = httpServletRequest.getSession();
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("loggedInUser", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
 
        httpServletResponse.sendRedirect("./");
        alexmallal.blog.core.model.User user = userService.getUserName(authUser.getUsername());
        user.setLastLoggedIn(new Date());
        this.userService.updateUser(user);
    }
}
