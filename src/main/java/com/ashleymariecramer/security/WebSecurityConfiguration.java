//package com.ashleymariecramer.security;
//
//// TODO: commenting out all this code until it is ready to be tested
//
//import com.ashleymariecramer.Employee;
//import com.ashleymariecramer.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.List;
//
//
//
//@Configuration //Allows spring to find these classes even though they are not public
//class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
////This class tqkes the email entered at login, searches database & returns a UserDetails object (if one exists)
//
//        @Autowired
//        EmployeeRepository employeeRepository;
//
//        @Override
//        public void init(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(userDetailsService());
//        }
//
//        @Bean
//        UserDetailsService userDetailsService() {
//            return new UserDetailsService() {
//
//                @Override
//                public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//                List<Employee> employees = employeeRepository.findEmployeesByUsername(name);
//                    if (!employees.isEmpty()) {
//                        Employee employee = employees.get(0);
//                        return new User(employee.getUsername(), employee.getPassword(),
//                                AuthorityUtils.createAuthorityList("USER"));
////									.commaSeparatedStringToAuthorityList("USER,ADMIN"));
//                        //only one role here = USER, to add multiple roles e.g., "INSTRUCTOR,STUDENT"
//                        //use: AuthorityUtils.commaSeparatedStringToAuthorityList("INSTRUCTOR,STUDENT"));
//                    } else {
//                        throw new UsernameNotFoundException("Unknown user: " + name);
//                    }
//
////                    if (!employees.isEmpty()) {
////                        Employee employee = employees.get(0);
//                        //We have three roles for now USER (for regular employees), MANAGER & ADMIN
////                        if (employee.getRole() == "user"){
////                            return new User(employee.getUsername(), employee.getPassword(),
////                                    AuthorityUtils.createAuthorityList("USER"));
//////                        }
////                        if (employee.getRole() == "manager"){
////                            return new User(employee.getUsername(), employee.getPassword(),
////                                    AuthorityUtils.createAuthorityList("MANAGER"));
////                        }
////                        if (employee.getRole() == "admin"){
////                            return new User(employee.getUsername(), employee.getPassword(),
////                                    AuthorityUtils.createAuthorityList("ADMIN"));
////                        }
////                    }
////                    throw new UsernameNotFoundException("Unknown user: " + name);
//                }
//            }; //end of new UserDetailsService()
//
//        } //end of userDetailsService
//} //end of GlobalAuthenticationConfigurerAdapter
//
//// This method looks up a user by name in your repository, and, if found, creates and returns a
//// org.springframework.security.core.userdetails.Employee object, with the stored user name, the stored password
//// for that user, and the role(s) that user has.
////TODO maybe i can have two parts here whereby if the user is an employee they can redirected to ome page and if not found in employee repo then searcehd manager repo instead
//
