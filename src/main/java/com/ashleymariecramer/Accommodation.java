package com.ashleymariecramer;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Accommodation {
    //---------------------Properties(private)----------------------------------
    @Id // id instance variable holds the database key for this class.
    @GeneratedValue(strategy= GenerationType.AUTO) // tells JPA to get the ID from the DBMS.
    private long id;
    @NotEmpty(message = "Please enter a name for the hostel")
    private String name;
    @NotEmpty(message = "Please enter a city for the hostel")
    private String city;
    @OneToMany(mappedBy="accommodation", fetch= FetchType.EAGER)
    private Set<Booking> Booking;
    @OneToMany(mappedBy="accommodation", fetch= FetchType.EAGER)
    private Set<Employee> Employee;

    // ---------------------Constructors(public)----------------------------------
    public Accommodation() { }

    public Accommodation(String name, String city) {
        this.name = name;
        this.city = city;
    }

    // ---------------------Methods(public)----------------------------------

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Booking> getBooking() {
        return Booking;
    }

    public Set<Employee> getEmployee() {
        return Employee;
    }




}
