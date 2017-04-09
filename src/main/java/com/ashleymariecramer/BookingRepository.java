package com.ashleymariecramer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingsByEmployee(@Param("name") String name); //Should be used to return employee_view of guest by logged in user
    Booking findByBookingNumber(String bookingNumber);
    List<Booking> findByAccommodationName(@Param("accomName") String accomName);
}