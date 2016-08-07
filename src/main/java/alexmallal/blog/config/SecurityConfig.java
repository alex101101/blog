package alexmallal.blog.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import alexmallal.blog.core.commons.custom.CustomAuthenticationSuccessHandler;
import alexmallal.blog.core.commons.custom.CustomLogoutSuccessHandler;
import alexmallal.blog.core.dao.UserDao;
import alexmallal.blog.core.dao.impl.UserDaoImpl;
import alexmallal.blog.core.services.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ComponentScan(basePackages = {"alexmallal.blog.core"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
//	@Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER").and()
//                .withUser("admin").password("admin").roles("ADMIN","USER");
//        
//    }

	
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/register").permitAll()
//                .antMatchers("/rest/**").hasRole("USER")
//                .antMatchers("/user").hasRole("USER")
//                .anyRequest().authenticated()
//                .and()
//    		    .formLogin().loginPage("/login").failureUrl("/login?error")
//    		    .successHandler(customSuccessHandler)
//    		    .usernameParameter("username").passwordParameter("password")		
//    		    .and()
//    		    .logout().logoutSuccessUrl("/login?logoutSuccess")
//    		    .logoutSuccessHandler(customLogoutSuccessHandler)
//    		    .and()
//    		    .csrf();
//    }
    
//    @Configuration
//    @Order(1)
//    public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter{
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.csrf().disable()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.POST, "/rest/**").authenticated()
//            .antMatchers(HttpMethod.PUT, "/rest/**").authenticated()
//            .antMatchers(HttpMethod.DELETE, "/rest/**").authenticated()
//            .anyRequest().permitAll()
//                        .and()
//                    .httpBasic();
//        }
//    }

    @Configuration
    @Order(2)
    public static class FormWebSecurityConfig extends WebSecurityConfigurerAdapter{

    	@Autowired
    	private CustomAuthenticationSuccessHandler customSuccessHandler;
    	
    	@Autowired
    	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
    	

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/post/*").permitAll()
            .antMatchers("/rest/blog/").permitAll()
            .antMatchers("/rest/blog/*").permitAll()
            .antMatchers("/rest/search/autocomplete/**").permitAll()
            .antMatchers("/rest/accountdetails/all").hasRole("ADMIN")
            .antMatchers("/rest/accountdetails/**").hasAnyRole("USER","ADMIN")
            .antMatchers("/user").hasRole("USER")
            .anyRequest().authenticated()
            .and()
		    .formLogin().loginPage("/login").loginProcessingUrl("/login").failureUrl("/login?error")
		    .successHandler(customSuccessHandler)
		    .usernameParameter("username").passwordParameter("password")		
		    .and()
		    .logout().permitAll().logoutSuccessUrl("/login?logoutSuccess")
		    .logoutSuccessHandler(customLogoutSuccessHandler)
		    .and()
		    .csrf();
        }
    }
    
    @Autowired
    private UserDetailsService userDetailsService;
     
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
    
    @Bean(name="authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
