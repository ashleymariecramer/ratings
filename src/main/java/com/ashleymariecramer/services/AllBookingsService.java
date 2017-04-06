package com.ashleymariecramer.services;

import com.ashleymariecramer.Booking;
import com.ashleymariecramer.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class AllBookingsService {

    @Autowired
    private BookingRepository bRepo;

    public Map<String, Object> makeAllBookingsDTO(Booking booking) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("bookingId", booking.getId());
        dto.put("bookingNumber", booking.getBookingNumber());
        dto.put("guestName", booking.getGuestFirstName() + " " + booking.getGuestSurname());
        dto.put("accommodation", booking.getAccommodationName());
        dto.put("reservationWebsite", booking.getReservationWebsite());
        dto.put("checkIn", booking.getCheckIn().toString());
//                getDayOfMonth() + "-" + barcelonaBooking.getCheckIn().getMonth()
//                + "-" + barcelonaBooking.getCheckIn().getYear()); //TODO this needs to be converted to a visible format
        dto.put("checkOut", booking.getCheckOut().getDayOfMonth() + "-" + booking.getCheckOut().getMonth()
                + "-" + booking.getCheckOut().getYear());
        dto.put("rating", booking.getRating());
        dto.put("reviewURL", booking.getReviewURL());
        dto.put("employee", booking.getEmployee().getFirstName() + " " + booking.getEmployee().getSurname());
        return dto;
    }


} //End of AllBookingsService