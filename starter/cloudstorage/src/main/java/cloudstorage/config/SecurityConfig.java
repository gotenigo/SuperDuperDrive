package cloudstorage.config;

import cloudstorage.services.Security.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    
    
    private AuthenticationService authenticationService;


    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/images/*","/signup", "/css/**", "/js/**","/h2-console/**").permitAll()
                .anyRequest().authenticated();

        //Which authentication methods are allowed (formLogin(), httpBasic()) and how they are configured.
        http.formLogin()   //form based authentication is supported
                .loginPage("/login") //define your custom login page
                .failureUrl("/login?error=true")
                .permitAll();  //to allow any access to any URL (i.e. /login and /login?error) associated to formLogin().



        http.
                csrf().disable(). // needed for H2 console mode
                headers().frameOptions().disable(); // needed for H2 console mode


        http.formLogin()
                .defaultSuccessUrl("/home", true);



        http
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll();

    }






/**
 * In order to help protect against CSRF attacks, by default, Spring Security Java Configuration log out requires:
 *
 * the HTTP method must be a POST
 *
 * the CSRF token must be added to the request. Since we have used @EnableWebSecurity and are using Thymeleaf, the CSRF token is automatically added as a hidden input for you (view the source to see it).
 *
 * If you were not using Spring MVC taglibs or Thymeleaf, you can access the CsrfToken on the ServletRequest using the attribute _csrf. You can find an example of including the CSRF token in a JSP within the Hello Spring Security Java Config.
 *
 *
 */



}
