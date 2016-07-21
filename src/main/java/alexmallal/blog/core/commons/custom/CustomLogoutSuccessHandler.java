package alexmallal.blog.core.commons.custom;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		if (auth != null && auth.getDetails() != null) {
            try {
                
            	//request.getSession().invalidate();
            	request.getSession().removeAttribute("loggedInUser");;

                System.out.println("User Successfully Logout!!!!!!");
                //update db for last active login
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 
        //response.setStatus(HttpServletResponse.SC_OK);
        //redirect to login
        response.sendRedirect("/blog/login?logoutSuccess");

		
	}

}
