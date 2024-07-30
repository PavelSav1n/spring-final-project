package ps.springfinalproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.springfinalproject.domain.Category;
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public Product create(Product product) {
        System.out.println("creating product... " + product);
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAllByCategoryId(long id) {
        return productRepository.findAllByCategoryId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAllByCategory(Category category) {
        return productRepository.findAllByCategoryId(category.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll() {
        productRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
