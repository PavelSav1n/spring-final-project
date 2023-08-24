package ps.springfinalproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.springfinalproject.domain.Role;
import ps.springfinalproject.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Role create(Role role) {
        System.out.println("Creating role... " + role); // debug
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Role> findById(long id) {
        return roleRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll() {
        roleRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }
}
