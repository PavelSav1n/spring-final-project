package ps.springfinalproject.services;

import ps.springfinalproject.domain.Category;
import ps.springfinalproject.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    // crud

    Product create(Product product);

    Optional<Product> findById(long id);

    Optional<Product> findByName(String name);

    List<Product> findAllByCategoryId(long id);

    List<Product> findAllByCategory(Category category);

    List<Product> findAll();

    void printAll();

    Product update(Product product);

    void delete(Product product);
}
