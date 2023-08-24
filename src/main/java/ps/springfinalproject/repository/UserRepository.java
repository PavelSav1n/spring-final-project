package ps.springfinalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.springfinalproject.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

//    public Optional<List<User>> findAllByName(String name);
//
//    public Optional<List<User>> findAllByRoleId(long roleId);
}
