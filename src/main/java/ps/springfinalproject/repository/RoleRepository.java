package ps.springfinalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.springfinalproject.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
