package com.ashleymariecramer.services;

import com.ashleymariecramer.Employee;
import com.ashleymariecramer.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;


@Service
public class AutenticationService {

    @Autowired
    private EmployeeRepository eRepo;

    /* this returns the users username or 'guest' (if no one logged in) */
    public String getUser(Authentication authentication) {
        if (!isGuest(authentication)) { //This checks there is not a guest user
            String loggedInUser = eRepo.findByUsername(authentication.getName()).getUsername();
            return loggedInUser;
        } else {
            String loggedInUser = "guest";
            return loggedInUser;
        }
    }

    /* this returns the users id number or 'null' (if no one logged in) */
    public Long getUserId(Authentication authentication) {
        if (!isGuest(authentication)) { //This checks there is not a guest user
            Long loggedInUser = eRepo.findByUsername(authentication.getName()).getId();
            return loggedInUser;
        } else {
            Long loggedInUser = null;
            return loggedInUser;
        }
    }


    public boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
        //this checks if authentication is null or is an instance the predefined spring security class "AnonymousAuthenticationToken"
    }

    public Map<String, Object> makeGuestUserDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("user", "guest");
        return dto;
    }

    public Map<String, Object> makeUserDTO(Employee employee, Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("loggedInUser", makeLoggedInUserDetailsDTO(employee)); // don´t need to loop here cos a game player only has one player
//        dto.put("roles", employee.getGamePlayers().stream().map(gp -> gp.getId())
//                .map(gpId -> makePlayersGameDetailsDTO(gpId, authentication)).collect(toList()));
        return dto;
    }

    //ASK : is this needed? Yes - using it to personalized each users dashboard
    private Map<String, Object> makeLoggedInUserDetailsDTO(Employee employee) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("userId", employee.getId());
        dto.put("user", employee.getUsername());
        dto.put("firstName", employee.getFirstName());
        dto.put("surname", employee.getSurname());
        dto.put("fullName", employee.getFirstName() + " " + employee.getSurname());
        dto.put("role", employee.getRole());
        dto.put("bookings", employee.getBookings().stream().map(b -> b.getBookingNumber()).collect(toList()));
        return dto;
    }
}

