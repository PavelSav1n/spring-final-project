package ps.springfinalproject.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ps.springfinalproject.domain.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private String id;
    @NotBlank(message = "Role name is required ")
    @Size(min = 3, max = 50, message = "Role name must be between 2 and 50 characters.")
    private String name;

    public static RoleDto toDto(Role role) {
        String id = String.valueOf(role.getId());

        return new RoleDto(id, role.getName());
    }

    public static Role fromDto(RoleDto roleDto) {
        if (roleDto.id == null) {
            roleDto.id = "0";
        }
        long id = Long.parseLong(roleDto.id);

        return new Role(id, roleDto.getName());
    }
}
