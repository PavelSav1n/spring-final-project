package ps.springfinalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.springfinalproject.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
