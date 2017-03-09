package com.ashleymariecramer.security;

/**
 //TODO: hiding this code for now so it can be tested later

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

//TODO: here the different end points and views can control what manager(ADMIN) or employees(USER) see.
//Add in Configuration Security to define which authentication method is used & which pages are restricted to USERS or ADMIN
@Configuration
@EnableWebSecurity
//TODO: think this is in red for now as I have deactivated the dependency for security in the pom.xml
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
//                    .antMatchers("/employee_view/**").hasAuthority("USER")
//                    .antMatchers("/manager_view/**" ).hasAuthority("ADMIN") //TODO: change this back after testing uncomment here & removes from permitALL
                    .antMatchers("index.html", "/", "", "/rest/**", "ratings.js", "/employee_view/**", "/manager_view/**" ).permitAll()//For pages that can be seen by all TODO: all visible for now
                    .and()
                    .formLogin() //This shows it uses form-based authentication
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginPage("/api/login")
                    .permitAll() // technically not needed but logical to have it here
                    .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .permitAll(); // technically not needed but logical to have it here
            //TODO: need to create 3 basic pages for now - index.html which is the login or sign up - accesible for all
            //TODO: manager view and employee view. Plus apis for login and logout

//  This code disables the CSFR tokens - and
            //turn off checking for CSRF tokens
            http.csrf().disable();

            //This overrides default settings that send HTML forms when unauthenticated access happens and when someone logs in or out.
            // With these changes, Spring just sents HTTP success and response codes, no HTML pages.
            // if user is not authenticated, just send an authentication failure response
            http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            // if login is successful, just clear the flags asking for authentication
            http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

            // if login fails, just send an authentication failure response
            http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

            // if logout is successful, just send a success response
            http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        }
        //This is a utility function, defined to remove the flag Spring sets when an unauthenticated user attempts to access some resource.
        private void clearAuthenticationAttributes(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }

        }
}

*/
