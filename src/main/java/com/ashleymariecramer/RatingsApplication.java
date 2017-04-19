package com.ashleymariecramer;


import com.ashleymariecramer.services.StringToLocalDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication  //This is needed to use SpringBoot
public class RatingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingsApplication.class, args); // method to launch an application

	}

	@Bean //this runs on startup
	public CommandLineRunner commandLineRunner(ApplicationContext ctx,
											   EmployeeRepository employeeRepo,
											   BookingRepository bookingRepo,
//											   AccommodationRepository accommodationRepo,
											   StringToLocalDateService stringToLocalDateService) {

		return args -> {

			// save a couple of employees
			employeeRepo.save(new Employee
					("Nattie", "Munchkins", "ashleymariecramer@gmail.com",
							"Nat2011", "111", "blackSwanBCN", "user"));
			employeeRepo.save(new Employee
					("Noey", "Boo", "ashleymariecramer@gmail.com",
							"Noeys2013", "111", "blackSwanSVQ", "user"));
			employeeRepo.save(new Employee
					("Elijah", "Robert", "ashleymariecramer@gmail.com", "ElijahBaby",
							"111", "blackSwanBCN", "user"));
			employeeRepo.save(new Employee
					("Nacho", "Honey", "ashleymariecramer@gmail.com",
							"Natx1973", "111", "all", "manager"));
			employeeRepo.save(new Employee
					("Alexandra", "Ola", "ashleymariecramer@gmail.com",
							"HolaOla", "111", "all", "admin"));
			employeeRepo.save(new Employee
					("Ashley", "Marie", "ashleymariecramer@gmail.com",
							"AMC1981", "111", "all", "developer"));

//			accommodationRepo.save(new Accommodation("Black Swan Barcelona", "Barcelona"));
//			accommodationRepo.save(new Accommodation("Black Swan Sevilla", "Sevilla"));

//TODO: there is some problem with creating bookings this way which causes problems calling api/manager_view
//			// create a few bookings
//			bookingRepo.save(new BarcelonaBooking("FTD123", "Minnie", "Mouse", "BarcelonaBooking",
//					stringToLocalDateService.convertStringToLocalDate("2017-01-01"),
//					stringToLocalDateService.convertStringToLocalDate("2017-01-03"),
//					-1.0, null, employeeRepo.findByUsername("Nat")));
//
//			bookingRepo.save(new BarcelonaBooking("GH&354", "Mickey", "Mouse", "HostelWorld",
//					stringToLocalDateService.convertStringToLocalDate("2017-02-13"),
//					stringToLocalDateService.convertStringToLocalDate("2017-02-53"),
//					-1.0, null, employeeRepo.findByUsername("Noeys")));
//
//			bookingRepo.save(new BarcelonaBooking("DGS163", "Donald", "Duck", "HostelWorld",
//					stringToLocalDateService.convertStringToLocalDate("2017-03-01"),
//					stringToLocalDateService.convertStringToLocalDate("2017-03-03"),
//					-1.0, null, employeeRepo.findByUsername("Markitus")));
//
//			bookingRepo.save(new BarcelonaBooking("EFG298", "Daisy", "Duck", "BarcelonaBooking",
//					stringToLocalDateService.convertStringToLocalDate("2017-03-20"),
//					stringToLocalDateService.convertStringToLocalDate("2017-03-27"),
//					-1.0, null, employeeRepo.findByUsername("Nat")));

//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}

		};
	}
}


@Configuration
		//Allows spring to find these classes even though they are not public
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

			@Override
			public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
				List<Employee> employees = employeeRepository.findEmployeesByUsername(name);
//				if (!employees.isEmpty()) {
//					Employee employee = employees.get(0);
//					return new User(employee.getUsername(), employee.getPassword(),
//							AuthorityUtils.createAuthorityList("USER"));
////									.commaSeparatedStringToAuthorityList("USER,ADMIN"));
//					//only one role here = USER, to add multiple roles e.g., "INSTRUCTOR,STUDENT"
//					//use: AuthorityUtils.commaSeparatedStringToAuthorityList("INSTRUCTOR,STUDENT"));
//				} else {
//					throw new UsernameNotFoundException("Unknown user: " + name);
//				}

                    if (!employees.isEmpty()) {
                        Employee employee = employees.get(0);
//We have 4 roles for now USER (for regular employees), MANAGER for all superiors, ADMIN (to create users), & DEVELOPER
                        if (employee.getRole() == "user"){
                            return new User(employee.getUsername(), employee.getPassword(),
                                    AuthorityUtils.createAuthorityList("USER"));
                        }
                        if (employee.getRole() == "manager"){
                            return new User(employee.getUsername(), employee.getPassword(),
                                    AuthorityUtils.createAuthorityList("MANAGER"));
                        }
                        if (employee.getRole() == "admin"){
                            return new User(employee.getUsername(), employee.getPassword(),
                                    AuthorityUtils.commaSeparatedStringToAuthorityList("MANAGER, ADMIN"));
                        }
                        if (employee.getRole() == "developer"){
                            return new User(employee.getUsername(), employee.getPassword(),
                                    AuthorityUtils.commaSeparatedStringToAuthorityList("MANAGER, ADMIN, DEVELOPER"));
                        }
                    }
                    throw new UsernameNotFoundException("Unknown user: " + name);
			}
		}; //end of new UserDetailsService()

	} //end of userDetailsService
} //end of GlobalAuthenticationConfigurerAdapter

// This method looks up a user by name in your repository, and, if found, creates and returns a
// org.springframework.security.core.userdetails.Employee object, with the stored user name, the stored password
// for that user, and the role(s) that user has.
//TODO maybe i can have two parts here whereby if the user is an employee they can redirected to ome page and if not found in employee repo then searcehd manager repo instead

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
				//USERS
				.antMatchers("/employee_view.html", "/employee_view", "/employee_view/**"
							 ).hasAuthority("USER")
				//MANAGERS
				.antMatchers("manager_view.html", "api/manager_view", "api/manager_view/**",
						"api/all_employees_usernames", "api/all_employees_usernames/**").hasAuthority("MANAGER")
				//ADMIN
				.antMatchers("/api/employees" ).hasAuthority("ADMIN")
				//DEVELOPERS
				.antMatchers("/rest/**" ).hasAuthority("DEVELOPER")
				//PERMIT ALL
				.antMatchers("ratings.js", "employee.js", "manager.js", "moment.js", "sweetalert.js", "sweetalertmin.js",
						"ratingStyle.css", "sweetalert.css", "/index.html" ).permitAll()
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


