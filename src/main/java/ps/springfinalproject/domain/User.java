package ps.springfinalproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
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
}
