package com.ashleymariecramer;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Employee {

    //---------------------Properties(private)----------------------------------
    // Double validation - in Javascript & JAVA - checks nickname, username and password are not empty @Empty
    @Id // id instance variable holds the database key for this class.
    @GeneratedValue(strategy= GenerationType.AUTO) // tells JPA to get the ID from the DBMS.
    private long id;
    @NotEmpty(message = "Please enter a first name")
    private String firstName;
    @NotEmpty(message = "Please enter a surname")
    private String surname;
//    @NotEmpty (message = "Please enter an email address")
//    @Email(message = "Please enter valid email address")
//    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address") //checks email has format x@y.z
//    private String email;
    @NotEmpty(message = "Please enter a username")
    private String username;
    @NotEmpty (message = "Please enter a password")
    private String password;
    @NotEmpty (message = "Please enter accommodation name")
    private String accommodationName;
    @OneToMany(mappedBy="employee", fetch= FetchType.EAGER)
    private Set<Booking> bookings;
//    @ManyToOne(fetch = FetchType.EAGER)   //Links to accommodation tables by accommodation id
//    @JoinColumn(name="accommodation_id")
//    private Accommodation accommodation;

    // ---------------------Constructors(public)----------------------------------
    public Employee() { }

    public Employee(String firstName, String surname, String username, String password, String accommodationName) {
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.accommodationName = accommodationName;
    }

    // ---------------------Methods(public)----------------------------------

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String email) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Booking> getBooking() {
        return bookings;
    }

    public void SetAccommodationName(String accommodation) {
        this.accommodationName = accommodationName;
    }

    public String getAccommodationName() {
        return accommodationName;
    }
}


