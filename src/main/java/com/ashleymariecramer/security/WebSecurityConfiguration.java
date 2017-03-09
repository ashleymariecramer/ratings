package com.ashleymariecramer.security;
/**
 * // TODO: commenting out all this code until it is ready to be tested

 import com.ashleymariecramer.Employee;
import com.ashleymariecramer.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;



@Configuration //Allows spring to find these classes even though they are not public
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
//This class tqkes the email entered at login, searches database & returns a UserDetails object (if one exists)

        @Autowired
        EmployeeRepository employeeRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Bean
        UserDetailsService userDetailsService() {
            return new UserDetailsService() {

// This method looks up a user by name in your repository, and, if found, creates and returns a
// org.springframework.security.core.userdetails.Employee object, with the stored user name, the stored password
// for that user, and the role(s) that user has.
//ASK: why is this in plural 'employees' as there is only ever one employee logged in at a time?
//ASK: Seems its a Spring thing cos plural works but using singular 'loggedInEmployee' did not
                @Override
                public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
                List<Employee> employees = employeeRepository.findEmployeesByUsername(name); //TODO: this is plural
                //Employee loggedInEmployee = employeeRepository.findEmployeeByUsername(name); //TODO: I'd make it singular
                    if (!employees.isEmpty()) {
                        Employee employee = employees.get(0);
                        return new User(employee.getUsername(), employee.getPassword(),
                                AuthorityUtils.commaSeparatedStringToAuthorityList("USER,ADMIN"));
                        //We have two roles for now USER for regular employees and ADMIN for managers
                    } else {
                        throw new UsernameNotFoundException("Unknown user: " + name);
                    }
                }
            };

        }
}**/

