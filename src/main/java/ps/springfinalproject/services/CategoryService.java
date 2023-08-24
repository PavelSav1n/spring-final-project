package ps.springfinalproject.services;

import ps.springfinalproject.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    // crud

    Category create(Category category);

    Optional<Category> findById(long id);

    Optional<Category> findByName(String name);

    List<Category> findAll();

    void printAll();

    Category update(Category category);

    void delete(Category category);

}
