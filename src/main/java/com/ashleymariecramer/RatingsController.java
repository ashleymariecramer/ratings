package com.ashleymariecramer;

import com.ashleymariecramer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private AccommodationRepository aRepo;
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
    private AccommodationService accommodationService;

    /*********************************** API / EMPLOYEES ****************************************/
    //1. Create new employees
    //Because some of the conditions have to return a Map ResponseEntity<Map<String,Object>> then all of them have to
    //@RequestParam is necessary before each parameter
    @RequestMapping(path = "/employees", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestParam String firstName,
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

    /****************************** API / GET ALL ACCOMMODATION NAMES **************************************/
    //2. Return a list of all accomodation saved in system
    @RequestMapping(path = "/all_accommodation", method = RequestMethod.GET)
    public List<Object> getAllAccomodation() {
        return aRepo.findAll().stream().map(accommodation -> accommodationService.makeAccommodationListDTO(accommodation))
                .collect(toList());
    }

    /****************************** API / GET ALL EMPLOYEES USERNAMES **************************************/
    //3. Return a list of all employees saved in system
    //TODO: Will need to separate list of employee names bcn & svl
    @RequestMapping(path = "/all_employees_usernames", method = RequestMethod.GET)
    public List<Object> getAllEmployees() {
        return eRepo.findAll().stream().map(employee -> employeeService.makeUsernameListDTO(employee))
                .collect(toList());
    }

    /****************************** API / BOOKINGS **************************************/
    //4. Create new bookings in the system
    @RequestMapping(path = "/bookings", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createBooking(@RequestParam String bookingNumber,
                                                            @RequestParam String guestFirstName,
                                                            @RequestParam String guestSurname,
                                                            @RequestParam String accommodation,
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
                    aRepo.findById(Long.parseLong(accommodation)), reservationWebsite,
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
    //5. List of All games to be shown whether user logged in or not
//    @RequestMapping(path = "/admin_view", method = RequestMethod.GET)
//    public List<Object> getAllBookings() {
//        return bRepo.findAll().stream().map(booking -> allBookingsService.makeAllBookingsDTO(booking)).collect(toList());
//    }

    /****************************** API / MANAGER VIEWs **************************************/
    //6. List of All games to be shown whether user logged in or not
    @RequestMapping(path = "/manager_view", method = RequestMethod.GET)
    public List<Object> getAllBookings() {
        //TODO: generate repo name from last 3 digits of API url + Repo
        return bRepo.findAll().stream().map(booking -> allBookingsService.makeAllBookingsDTO(booking)).collect(toList());
    }


    /****************************** API / EMPLOYEE VIEW **************************************/
    //7. List of All games to be shown whether user logged in or not
    @RequestMapping(path = "/employee_view", method = RequestMethod.GET)
    public List<Object> getAllBookingsByEmployee() {
        //TODO: here instead of findAll need find by logged in employee
        return bRepo.findAll().stream().map(booking -> allBookingsAssignedToEmployeeService.makeAllBookingsDTO(booking)).collect(toList());
    }


} // End of RatingsController

