package alexmallal.blog.core.services;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    String findLoggedInUsername();

    public boolean autoLogin( String username, String password, HttpServletRequest request);
}