package ps.springfinalproject.services;

import ps.springfinalproject.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

//    Optional<List<User>> findAllByName(String email);
//
//    Optional<List<User>> findAllByRoleId(long roleId);

    List<User> findAll();

    void printAll();

    User update(User user);

    void delete(User user);

}
