package com.ashleymariecramer;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Guest {
    //---------------------Properties(private)----------------------------------
    // Double validation - in Javascript & JAVA - checks nickname, username and password are not empty @Empty
    @Id // id instance variable holds the database key for this class.
    @GeneratedValue(strategy= GenerationType.AUTO) // tells JPA to get the ID from the DBMS.
    private long id;
    private String firstName;
    private String surname;
    private String email;
    //TODO: map booking associated with guest
    @OneToMany(mappedBy="guest", fetch= FetchType.EAGER)
    private Set<Booking> booking;


    // ---------------------Constructors(public)----------------------------------
    public Guest() { }

    public Guest(String firstName, String surname, String email ) {
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
    }

    // ---------------------Methods(public)----------------------------------

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
        this.email = email;
    }

    public long getId() {
        return id;
    }  // no setter required as generated automatically

}


