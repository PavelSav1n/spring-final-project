package ps.springfinalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ps.springfinalproject.domain.Role;

@Data
@AllArgsConstructor
public class RoleDto {
    private String id;
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
