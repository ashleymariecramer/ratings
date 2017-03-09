package com.ashleymariecramer;

import com.ashleymariecramer.enumeration.ReservationWebsite;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ashleymariecramer on 08/03/2017.
 */
@Entity
public class Booking {
    // Double validation - in Javascript & JAVA - checks nickname, username and password are not empty @Empty
    @Id // id instance variable holds the database key for this class.
    @GeneratedValue(strategy= GenerationType.AUTO) // tells JPA to get the ID from the DBMS.
    private long id;
    private long bookingNumber; //code provided from booking system
    private ReservationWebsite reservationWebsite;
    private Date checkIn;
    private Date checkOut;
    private double rating; // rating E(from either booking.com or hostelworld.com
    private String reviewURL; //link to guest review online
    @ManyToOne(fetch = FetchType.EAGER)   //Links to employee tables by employee id
    @JoinColumn(name="employee_id")
    private Employee employee;
    @ManyToOne(fetch = FetchType.EAGER)   //Links guest and employee tables by employee id
    @JoinColumn(name="guest_id")
    private Guest guest;


    // ---------------------Constructors(public)----------------------------------
    public Booking() { }

    public Booking(long bookingNumber, ReservationWebsite reservationWebsite, Date checkIn, Date checkOut,
                   double rating, String reviewURL ) {
        this.bookingNumber = bookingNumber;
        this.reservationWebsite = reservationWebsite;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.rating = rating;
        this.reviewURL = reviewURL;
    }

    // ---------------------Methods(public)----------------------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(long bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public ReservationWebsite getReservationWebsite() {
        return reservationWebsite;
    }

    public void setReservationWebsite(ReservationWebsite reservationWebsite) {
        this.reservationWebsite = reservationWebsite;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReviewURL() {
        return reviewURL;
    }

    public void setReviewURL(String reviewURL) {
        this.reviewURL = reviewURL;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

}
