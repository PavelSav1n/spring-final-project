package ps.springfinalproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.springfinalproject.domain.User;
import ps.springfinalproject.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public User create(User user) {
        System.out.println("creating user... " + user);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

//    @Transactional(readOnly = true)
//    @Override
//    public Optional<List<User>> findAllByName(String name) {
//        return userRepository.findAllByName(name);
//    }
//
//    @Override
//    public Optional<List<User>> findAllByRoleId(long roleId) {
//        return userRepository.findAllByRoleId(roleId);
//    }

    @Transactional(readOnly = true)
    @Override
    public void printAll() {
        userRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
