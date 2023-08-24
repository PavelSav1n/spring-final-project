package ps.springfinalproject.services;

import ps.springfinalproject.domain.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    // crud

    Role create(Role role);

    Optional<Role> findById(long id);

    Optional<Role> findByName(String name);

    List<Role> findAll();

    void printAll();

    Role update(Role role);

    void delete(Role role);
}
