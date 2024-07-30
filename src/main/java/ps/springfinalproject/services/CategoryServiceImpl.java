package ps.springfinalproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.springfinalproject.domain.Category;
import ps.springfinalproject.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Category create(Category category) {
        System.out.println("creating category... " + category);
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll() {
        categoryRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
