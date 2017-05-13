package com.ashleymariecramer;

import com.ashleymariecramer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    private StringToLocalDateService stringToLocalDateService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private AutenticationService authenticationService;

    /*********************************** API CREATE EMPLOYEES ****************************************/
    //1. Create new employees
    //Because some of the conditions have to return a Map ResponseEntity<Map<String,Object>> then all of them have to
    //@RequestParam is necessary before each parameter
    @RequestMapping(path = "/employees", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestParam String firstName,
                                                            @RequestParam String surname,
                                                            @RequestParam String email,
                                                            @RequestParam String username,
                                                            @RequestParam String password,
                                                            @RequestParam String accommodationName,
                                                            @RequestParam String role) {

        Employee employee = eRepo.findByUsername(username); //gives a 409 Conflict Error

// validation to check employee not already created
        if (employee != null) {
            return new ResponseEntity<Map<String, Object>>
                    (entityConstructionService.makeMap("error", "Username already in use"), HttpStatus.CONFLICT);
        } else {
            employee = eRepo.save(new Employee(firstName, surname, email, username, password,
                    accommodationName, role)); //gives a 201 Created message
            return new ResponseEntity<Map<String, Object>>
                    (entityConstructionService.makeMap("employee", employee.getUsername()), HttpStatus.CREATED);
        }
    }


    /******************************* API /CURRENT USER ********************************************/
    //determines if a user is logged in and if so returns their username otherwise assigns username 'guest' (which would not let the user do anything other than login

    @RequestMapping(path = "/currentUser", method = RequestMethod.GET)
    public Map<String, Object> getUser(Authentication authentication) {
        if (!authenticationService.isGuest(authentication)) { //This checks there is not a guest user
            Employee loggedInUser = eRepo.findByUsername(authentication.getName());
            return authenticationService.makeUserDTO(loggedInUser, authentication);
        }
        return authenticationService.makeGuestUserDTO();
    }

    /******************************* API /CURRENT USER ID NUMBER********************************************/
    //determines if a user is logged in and if so returns their username otherwise assigns username 'guest' (which would not let the user do anything other than login
//ASK: code here to just get id number nothing more

    @RequestMapping(path = "/currentUserId", method = RequestMethod.GET)
    public Long getUserId(Authentication authentication) {
        if (!authenticationService.isGuest(authentication)) { //This checks there is not a guest user
            Employee loggedInUser = eRepo.findByUsername(authentication.getName());
            return authenticationService.getUserId(authentication); //this is getting the id correctly but problem on js side
        }
        return null;
    }

    /****************************** GET EMPLOYEES USERNAMES **************************************/
    //3a. Return a list of all employees saved in system
    @RequestMapping(path = "/all_employees_usernames", method = RequestMethod.GET)
    public List<Object> getAllEmployees() {
        return eRepo.findAll().stream()
                .map(employee -> employeeService.makeUsernameListDTO(employee))
                .collect(toList());
    }

    //3b. Return a list of employees by accommodation name
    @RequestMapping("/all_employees_usernames/{accomName}")
    public List <Object> findEmployeesByAccomodationName(@PathVariable String accomName) {
        return eRepo
                .findByAccommodationName(accomName)
                .stream()
                .map(employee -> employeeService.makeUsernameListDTO(employee))
                .collect(toList());

    }

    /****************************** API CREATE BOOKINGS **************************************/
    //4. Create new bookings in the system
    @RequestMapping(path = "/bookings", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createBooking(@RequestParam String bookingNumber,
                                                            @RequestParam String guestFirstName,
                                                            @RequestParam String guestSurname,
                                                            @RequestParam String accommodationName,
                                                            @RequestParam String reservationWebsite,
                                                            @RequestParam String checkInDate,
                                                            @RequestParam String checkOutDate,
                                                            @RequestParam String employee) {
        Booking booking = bRepo.findByBookingNumber(bookingNumber); //gives a 409 Conflict Error

// //TODO: check validation to check barcelonaBooking not already created working correctly
        if (booking != null) {
            return new ResponseEntity<Map<String, Object>>
                    (entityConstructionService.makeMap("error", "Booking Number already in use"), HttpStatus.CONFLICT);
        } else {
            booking = bRepo.save(new Booking(bookingNumber, guestFirstName, guestSurname,
                    accommodationName, reservationWebsite,
                    stringToLocalDateService.convertStringToLocalDate(checkInDate),
                    stringToLocalDateService.convertStringToLocalDate(checkOutDate),
                    -1, null,
                    eRepo.findByUsername(employee)));//gives a 201 Created message
            //TODO: rating and review should be empty in this case but to initialise them the values
            //TODO: null for review & -1 for rating (so its clear it's not a real rating - as these must be 0 or more)

            return new ResponseEntity<Map<String, Object>>

                    (entityConstructionService.makeMap("Booking", booking.getCheckIn()), HttpStatus.CREATED);
        }
    }

    /****************************** API / ADMIN VIEW **************************************/
//    5. List of All games to be shown whether user logged in or not
//    @RequestMapping(path = "/admin_view", method = RequestMethod.GET)
//    public List<Object> getAllBookingsAdmin() {
//        return bRepo.findAll().stream().map(booking -> allBookingsService.makeAllBookingsDTO(booking)).collect(toList());
//    }

    /****************************** API MANAGER VIEWS OF BOOKINGS **************************************/
    //6a. Return a list of all bookings saved in system
    @RequestMapping(path = "/manager_view", method = RequestMethod.GET)
    public List<Object> getAllBookings() {
        return bRepo.findAll().stream()
                .map(booking -> allBookingsService.makeAllBookingsDTO(booking))
                .collect(toList());
    }

    //6b. Return a list of booking by accommodation name
    @RequestMapping("/manager_view/{accomName}")
    public List <Object> getAllBookingsByAccomodationName(@PathVariable String accomName) {
        return bRepo.findByAccommodationName(accomName)
                .stream()
                .map(booking -> allBookingsService.makeAllBookingsDTO(booking))
                .collect(toList());
    }

    /****************************** API EMPLOYEE VIEW **************************************/
//    7. List of All bookings related to a specific employee
    @RequestMapping(path = "/employee_view/{employeeId}", method = RequestMethod.GET)
    public List<Object> getAllBookingsByEmployee(@PathVariable Long employeeId) {
        return eRepo.findOne(employeeId)
                .getBookings()
                .stream()
                .map(booking -> allBookingsService.makeAllBookingsDTO(booking))
                .collect(toList());
    }
//    public List<Object> getAllBookingsByEmployee(@RequestParam("employeeId") Long employeeId, Authentication authentication) {
//        //ASK: can i just reassign user id here????
//        Employee loggedInUser = eRepo.findByUsername(authentication.getName());
//        employeeId = authenticationService.getUserId(authentication); //this is getting the id correctly but problem on js side
//        System.out.println(employeeId);
//        return eRepo.findOne(employeeId)
//                .getBookings()
//                .stream()
//                .map(booking -> allBookingsService.makeAllBookingsDTO(booking))
////                .map(booking -> allBookingsAssignedToEmployeeService.makeAllBookingsDTO(booking))
//                .collect(toList());
//    }

} // End of RatingsController

