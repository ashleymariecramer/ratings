package com.ashleymariecramer;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
public class Employee {

    //---------------------Properties(private)----------------------------------
    @Id // id instance variable holds the database key for this class.
    @GeneratedValue(strategy= GenerationType.AUTO) // tells JPA to get the ID from the DBMS.
    private long id;
    @NotEmpty(message = "Please enter a first name")
    private String firstName;
    @NotEmpty(message = "Please enter a surname")
    private String surname;
    @NotEmpty (message = "Please enter an email address")
    @Email(message = "Please enter valid email address")
    @Pattern(regexp=".+@.+\\..+") //checks email has format x@y.z
    private String email;
    @NotEmpty(message = "Please enter a username")
    private String username;
    @NotEmpty (message = "Please enter a password")
    private String password;
    @NotEmpty (message = "Please enter accommodation name")
    private String accommodationName;
    private String role;
    @OneToMany(mappedBy="employee", fetch= FetchType.EAGER)
    private Set<Booking> bookings;



    // ---------------------Constructors(public)----------------------------------
    public Employee() { }

    public Employee(String firstName, String surname, String email,
                    String username, String password,
                    String accommodationName, String role) {
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.accommodationName = accommodationName;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.username = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void SetAccommodationName(String accommodation) {
        this.accommodationName = accommodationName;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.password = role;
    }

}


