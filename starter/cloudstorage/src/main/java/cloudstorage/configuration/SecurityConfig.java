package cloudstorage.configuration;

import cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    
    
/*
    String[] staticResources  =  {
            "/css/**",
            "/images/**",
            "/fonts/**",
            "/scripts/**",
            "/jquery/**",
            "/js/**",
            "/scss/**",
            "/.sass-cache/**"
    };*/

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
                //.antMatchers(staticResources).permitAll()
                .antMatchers("/images/*","/register", "/css/**", "/js/**","/h2-console/**").permitAll()
                .anyRequest().authenticated();

        //Which authentication methods are allowed (formLogin(), httpBasic()) and how they are configured.
        http.formLogin()   //form based authentication is supported
                .loginPage("/login") //define your custom login page
                .failureUrl("/login?error=true")
                //.failureForwardUrl("/login?error")
                .permitAll();  //to allow any access to any URL (i.e. /login and /login?error) associated to formLogin().
        //	When authentication fails, the browser is redirected to /login?error
        //.httpBasic(); /*HTTP Basic authentication*/  //This default configuration is why your application is on lock-down, as soon as you add Spring Security to it. Simple, isn’t it?
        //	When we are successfully logged out, the browser is redirected to /login?logout so we can display an logout success message by detecting if the parameter logout is non-null.
                /*.and()
                .logout()
                .permitAll();*/

        http.
                csrf().disable(). // needed for H2 console mode
                headers().frameOptions().disable(); // needed for H2 console mode

        http.formLogin()
                .defaultSuccessUrl("/chat", true);



        http
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll();

    }




    //only for testing purpose
    /*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }*/


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
