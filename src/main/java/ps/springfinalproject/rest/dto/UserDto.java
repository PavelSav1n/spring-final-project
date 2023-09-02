package ps.springfinalproject.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ps.springfinalproject.domain.Role;
import ps.springfinalproject.domain.User;


@Data
@AllArgsConstructor
@NoArgsConstructor // For passing model to view to use th:field
@Accessors(chain = true) // For more convenient view of object creation & tests.
public class UserDto {

    private String id;
    @NotBlank(message = "Name is required") // (not to be just white spaces)
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email. Try again.")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Password is required")
    private String password2;
    @NotBlank(message = "Please, specify your date of birth")
    private String birthDate;
    @NotBlank(message = "Choose a role for this user")
    private String roleId;
    private String roleName;

    public static UserDto toDto(User user) {
        String id = String.valueOf(user.getId());
        String roleId = String.valueOf(user.getRole().getId());
        String roleName = user.getRole().getName();
        return new UserDto()
                .setId(id)
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setPassword2(user.getPassword())
                .setBirthDate(user.getBirthDate())
                .setRoleId(roleId)
                .setRoleName(roleName);
    }

    public static User fromDto(UserDto userDto) {
        if (userDto.id == null) {
            userDto.id = "0";
        }
        long id = Long.parseLong(userDto.id);

        System.out.println("USER : fromDto");
        System.out.println("userDto.roleId = " + userDto.roleId);
        System.out.println("userDto.roleName = " + userDto.roleName);

        return new User(
                id,
                userDto.name,
                userDto.email,
                userDto.password,
                userDto.birthDate,
                new Role(Long.parseLong(userDto.roleId), userDto.roleName));
    }
}
