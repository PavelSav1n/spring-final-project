package ps.springfinalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.springfinalproject.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    List<Product> findAllByCategoryId(long id);
}
