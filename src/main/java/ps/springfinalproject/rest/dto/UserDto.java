package ps.springfinalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ps.springfinalproject.domain.Role;
import ps.springfinalproject.domain.User;

@Data
@AllArgsConstructor
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private String birthDate;
    private String roleId;
    private String roleName;

    public static UserDto toDto(User user) {
        String id = String.valueOf(user.getId());
        String roleId = String.valueOf(user.getRole().getId());
        String roleName = user.getRole().getName();
        return new UserDto(id, user.getName(), user.getEmail(), user.getPassword(), user.getBirthDate(), roleId, roleName);
    }

    public static User fromDto(UserDto userDto) {
        if (userDto.id == null) {
            userDto.id = "0";
        }
        long id = Long.parseLong(userDto.id);
        return new User(id, userDto.name, userDto.email, userDto.password, userDto.birthDate, new Role(Long.parseLong(userDto.roleId), userDto.roleName));
    }
}
