package alexmallal.blog.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.*;

//extend- initializes filter chain
public class SecurityWebApplicationInitializer
      extends AbstractSecurityWebApplicationInitializer {
	//initialise securityconfig file
    public SecurityWebApplicationInitializer() {
        //super(SecurityConfig.class);
    }
}
