package com.ashleymariecramer;

import com.ashleymariecramer.services.AllBookingsAssignedToEmployeeService;
import com.ashleymariecramer.services.AllBookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class RatingsController {

    @Autowired //this injects an instance of the classes to be used by this controller (Dependency Injection)
    private EmployeeRepository eRepo;
    @Autowired
    private GuestRepository gRepo;
    @Autowired
    private BookingRepository bRepo;
    @Autowired
    private AllBookingsService allBookingsService;
    @Autowired
    private AllBookingsAssignedToEmployeeService allBookingsAssignedToEmployeeService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    /****************************** API / ADMIN VIEW **************************************/
    //1. List of All games to be shown whether user logged in or not
    @RequestMapping(path = "/admin_view", method = RequestMethod.GET)
    public List<Object> getAllBookings() {
        return bRepo.findAll().stream().map(booking -> allBookingsService.makeAllBookingsDTO(booking)).collect(toList());
    }

    /****************************** API / EMPLOYEE VIEW **************************************/
    //2. List of All games to be shown whether user logged in or not
    @RequestMapping(path = "/employee_view", method = RequestMethod.GET)
    public List<Object> getAllBookingsByEmployee() {
        //TODO: here instead of findAll need find by logged in employee
        return bRepo.findAll().stream().map(booking -> allBookingsAssignedToEmployeeService.makeAllBookingsDTO(booking)).collect(toList());
    }


} // End of RatingsController

