package com.ashleymariecramer;


import com.ashleymariecramer.services.StringToLocalDateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


import java.util.Arrays;

@SpringBootApplication  //This is needed to use SpringBoot
public class RatingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingsApplication.class, args); // method to launch an application

	}

	@Bean //this runs on startup
	public CommandLineRunner commandLineRunner(ApplicationContext ctx,
											   EmployeeRepository employeeRepo,
											   BookingRepository bookingRepo,
											   StringToLocalDateService stringToLocalDateService) {

		return args -> {

			// save a couple of employees
			employeeRepo.save(new Employee
					("Nattie", "Munchkins", "Nat", "111"));
			employeeRepo.save(new Employee
					("Noey", "Boo", "Noeys", "111"));
			employeeRepo.save(new Employee
					("Marky", "Mark", "Markitus", "111"));

//TODO: there is some problem with creating bookings this way which causes problems calling api/manager_view
//			// create a few bookings
//			bookingRepo.save(new Booking("FTD123", "Minnie", "Mouse", "Booking",
//					stringToLocalDateService.convertStringToLocalDate("2017-01-01"),
//					stringToLocalDateService.convertStringToLocalDate("2017-01-03"),
//					-1.0, null, employeeRepo.findByUsername("Nat")));
//
//			bookingRepo.save(new Booking("GH&354", "Mickey", "Mouse", "HostelWorld",
//					stringToLocalDateService.convertStringToLocalDate("2017-02-13"),
//					stringToLocalDateService.convertStringToLocalDate("2017-02-53"),
//					-1.0, null, employeeRepo.findByUsername("Noeys")));
//
//			bookingRepo.save(new Booking("DGS163", "Donald", "Duck", "HostelWorld",
//					stringToLocalDateService.convertStringToLocalDate("2017-03-01"),
//					stringToLocalDateService.convertStringToLocalDate("2017-03-03"),
//					-1.0, null, employeeRepo.findByUsername("Markitus")));
//
//			bookingRepo.save(new Booking("EFG298", "Daisy", "Duck", "Booking",
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
