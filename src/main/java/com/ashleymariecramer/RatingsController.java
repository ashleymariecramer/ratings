package com.ashleymariecramer;

import com.ashleymariecramer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class RatingsController {

    @Autowired //this injects an instance of the classes to be used by this controller (Dependency Injection)
    private EmployeeRepository eRepo;
    @Autowired
    private BookingRepository bRepo;
    @Autowired
    private AllBookingsService allBookingsService;
    @Autowired
    private AllBookingsAssignedToEmployeeService allBookingsAssignedToEmployeeService;
    @Autowired
    private EntityConstructionService entityConstructionService;
    @Autowired
    private StringToDateService stringToDateService;
    @Autowired
    private EmployeeService employeeService;

//    @RequestMapping("/")
//    public String index() {
//        return "Greetings from Spring Boot!";
//    }

    /*********************************** API / EMPLOYEES ****************************************/
    //1. Create new employees
    //Because some of the conditions have to return a Map ResponseEntity<Map<String,Object>> then all of them have to
    //@RequestParam is necessary before each parameter
    @RequestMapping(path = "/employees", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createPlayer(@RequestParam String firstName,
                                                            @RequestParam String surname,
                                                            @RequestParam String username,
                                                            @RequestParam String password) {
        Employee employee = eRepo.findByUsername(username); //gives a 409 Conflict Error

// //TODO: check validation to check employee not already created working correctly
// return validateEmployee(firstName, surname, username, password);
//        return validationService.validatePlayer(firstName, surname, username, password);
        if (employee != null) {
            return new ResponseEntity<Map<String, Object>>
                    (entityConstructionService.makeMap("error", "Username(email) already in use"), HttpStatus.CONFLICT);
        } else {
            employee = eRepo.save(new Employee(firstName, surname, username, password)); //gives a 201 Created message
            return new ResponseEntity<Map<String, Object>>
                    (entityConstructionService.makeMap("employee", employee.getUsername()), HttpStatus.CREATED);
        }
    }

    /****************************** API / GET ALL EMPLOYEES USERNAMES **************************************/
    //2. Return a list of all employees saved in system
    @RequestMapping(path = "/all_employees_usernames", method = RequestMethod.GET)
    public List<Object> getAllEmployees() {
        return eRepo.findAll().stream().map(employee -> employeeService.makeUsernameListDTO(employee))
                .collect(toList());
    }


    /****************************** API / BOOKINGS **************************************/
    //3. Create new bookings in the system
    @RequestMapping(path = "/bookings", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createPlayer(@RequestParam String bookingNumber,
                                                            @RequestParam String guestFirstName,
                                                            @RequestParam String guestSurname,
                                                            @RequestParam String reservationWebsite,
                                                            @RequestParam String checkInDate,
                                                            @RequestParam String checkOutDate,
                                                            @RequestParam String employee) {
        Booking booking = bRepo.findByBookingNumber(bookingNumber); //gives a 409 Conflict Error

// //TODO: check validation to check booking not already created working correctly
        if (booking != null) {
            return new ResponseEntity<Map<String, Object>>
                    (entityConstructionService.makeMap("error", "Booking Number already in use"), HttpStatus.CONFLICT);
        } else {
            booking = bRepo.save(new Booking(bookingNumber, guestFirstName, guestSurname, reservationWebsite,
                    stringToDateService.convertStringToDate(checkInDate),
                    stringToDateService.convertStringToDate(checkOutDate),
                    -1, null,
                    eRepo.findByUsername(employee)));//gives a 201 Created message
            //TODO: rating and review should be empty in this case but to intialize them the values
            //TODO: null for review & -1 for rating (so its clear it's not a real rating - as these must be 0 or more)

            return new ResponseEntity<Map<String, Object>>
                    (entityConstructionService.makeMap("booking", booking.getId()), HttpStatus.CREATED);
        }

    }
    /****************************** API / ADMIN VIEW **************************************/
    //4. List of All games to be shown whether user logged in or not
    @RequestMapping(path = "/admin_view", method = RequestMethod.GET)
    public List<Object> getAllBookings() {
        return bRepo.findAll().stream().map(booking -> allBookingsService.makeAllBookingsDTO(booking)).collect(toList());
    }

    /****************************** API / EMPLOYEE VIEW **************************************/
    //5. List of All games to be shown whether user logged in or not
    @RequestMapping(path = "/employee_view", method = RequestMethod.GET)
    public List<Object> getAllBookingsByEmployee() {
        //TODO: here instead of findAll need find by logged in employee
        return bRepo.findAll().stream().map(booking -> allBookingsAssignedToEmployeeService.makeAllBookingsDTO(booking)).collect(toList());
    }


} // End of RatingsController

