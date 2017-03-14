package com.ashleymariecramer.services;

import com.ashleymariecramer.Booking;
import com.ashleymariecramer.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class AllBookingsAssignedToEmployeeService {

    @Autowired
    private BookingRepository bRepo;

    public Map<String, Object> makeAllBookingsDTO(Booking booking) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("bookingId", booking.getId());
        dto.put("bookingNumber", booking.getBookingNumber());
        dto.put("reservationWebsite", booking.getReservationWebsite());
        dto.put("checkIn", booking.getCheckIn());
        dto.put("checkOut", booking.getCheckOut());
        dto.put("guestFirstName", booking.getGuestFirstName());
        dto.put("guestSurname", booking.getGuestSurname());

        return dto;
    }



} //End of AllBookingsAssignedToEmployeeService
