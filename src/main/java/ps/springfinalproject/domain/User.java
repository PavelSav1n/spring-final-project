package ps.springfinalproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //    @NotBlank(message = "Name is required") // (not to be just white spaces)
//    @Size(min = 2, max = 50, message = "Message must be between 2 and 50 characters.")
    private String name;
    //    @NotBlank(message = "Email is required")
//    @Email(message = "Invalid email. Try again.")
    private String email;
    private String password;
    @Column(name = "bdate")
    private String birthDate;
    // CascadeType.ALL will throw an InvalidDataAccessApiUsageException because Role is already in persist
    // fetch = FetchType.EAGER -- is default value for @ManyToOne
    @ManyToOne(targetEntity = Role.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    private Role role;

    public User(String name, String email, String password, String birthDate, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.role = role;
    }

    // For OrderDto (toDto). Other fields don't matter because we are using only id and name
    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
