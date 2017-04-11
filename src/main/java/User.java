//import com.ashleymariecramer.Booking;
//import com.ashleymariecramer.Role;
//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.NotEmpty;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//
//import javax.persistence.*;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
//import java.util.Set;
//
//@Entity
//public class User {
//
//    @Id // id instance variable holds the database key for this class.
//    @GeneratedValue(strategy= GenerationType.AUTO) // tells JPA to get the ID from the DBMS.
//    private long id;
//    @NotEmpty(message = "Please enter a first name")
//    private String firstName;
//    @NotEmpty(message = "Please enter a surname")
//    private String surname;
//    @NotEmpty (message = "Please enter an email address")
//    @Email(message = "Please enter valid email address")
//    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address") //checks email has format x@y.z
//    private String email;
//    @NotEmpty(message = "Please enter a username")
//    @Column(unique = true) // TO ensure the username is unique
//    @Size(min = 6, max = 20)
//    private String username;
//    @NotEmpty (message = "Please enter a password")
//    private String password;
//    @NotEmpty (message = "Please enter accommodation name")
//    private String accommodationName;
//    @OneToMany(mappedBy="user", fetch= FetchType.EAGER)
//    private Set<Booking> bookings;
//
//    @Column(nullable = false) //cannot be null in Database
//    private boolean enabled;
//    private Role role;
//}
