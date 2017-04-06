//package com.ashleymariecramer;
//
//import org.hibernate.validator.constraints.NotEmpty;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//public class Accommodation {
//    //---------------------Properties(private)----------------------------------
//    @Id // id instance variable holds the database key for this class.
//    @GeneratedValue(strategy= GenerationType.AUTO) // tells JPA to get the ID from the DBMS.
//    private long id;
//    @NotEmpty(message = "Please enter the accommodation name")
//    private String name;
//    @NotEmpty(message = "Please enter the city")
//    private String city;
//    @OneToMany(mappedBy="accommodation", fetch= FetchType.EAGER)
//    private Set<Booking> bookings;
//    @OneToMany(mappedBy="accommodation", fetch= FetchType.EAGER)
//    private Set<Employee> employees;
//
//    // ---------------------Constructors(public)----------------------------------
//    public Accommodation() { }
//
//    public Accommodation(String name, String city) {
//        this.name = name;
//        this.city = city;
//    }
//
//    // ---------------------Methods(public)----------------------------------
//
//    public long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public Set<Booking> getBookings() {
//        return bookings;
//    }
//
//    public Set<Employee> getEmployees() {
//        return employees;
//    }
//
//
//
//
//}
