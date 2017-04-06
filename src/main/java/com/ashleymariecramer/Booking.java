package com.ashleymariecramer;



import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Booking {

    // Double validation - in Javascript & JAVA - checks nickname, username and password are not empty @Empty
    @Id // id instance variable holds the database key for this class.
    @GeneratedValue(strategy= GenerationType.AUTO) // tells JPA to get the ID from the DBMS.
    private long id;
    private String bookingNumber; //code provided from booking system
    private String guestFirstName;
    private String guestSurname;
    private String accommodationName;
    private String reservationWebsite;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double rating; // rating either from either booking.com or hostelworld.com
    private String reviewURL; //link to guest review online
    @ManyToOne(fetch = FetchType.EAGER)   //Links to employee tables by employee id
    @JoinColumn(name="employee_id")
    private Employee employee;
//    @ManyToOne(fetch = FetchType.EAGER)   //Links to employee tables by employee id
//    @JoinColumn(name="accommodation_id")




    // ---------------------Constructors(public)----------------------------------
    public Booking() { }

    public Booking(String bookingNumber, String guestFirstName, String guestSurname,
                   String accommodationName, String reservationWebsite, LocalDate checkIn, LocalDate checkOut,
                   double rating, String reviewURL, Employee employee ) {
        this.bookingNumber = bookingNumber;
        this.guestFirstName = guestFirstName;
        this.guestSurname = guestSurname;
        this.accommodationName = accommodationName;
        this.reservationWebsite = reservationWebsite;
//        Date dateIn = new Date();
//        this.checkIn = checkIn.from(dateIn.toInstant());
//        Date dateOut = new Date();
//        this.checkOut = checkOut.from(dateOut.toInstant());
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.rating = rating;
        this.reviewURL = reviewURL;
        this.employee = employee;
    }

    // ---------------------Methods(public)----------------------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getGuestFirstName() {
        return guestFirstName;
    }

    public void setGuestFirstName(String guestFirstName) {
        this.guestFirstName = guestFirstName;
    }

    public String getGuestSurname() {
        return guestSurname;
    }

    public void setGuestSurname(String guestSurname) {
        this.guestSurname = guestSurname;
    }

    public String getReservationWebsite() {
        return reservationWebsite;
    }

    public void setReservationWebsite(String reservationWebsite) {
        this.reservationWebsite = reservationWebsite;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
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

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }



}
