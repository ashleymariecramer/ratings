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
					("Ashley", "Marie", "ashleymariecramer@gmail.com",
							"AMC1981", "111", "all", "admin"));

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
