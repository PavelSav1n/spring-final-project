package ps.springfinalproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.domain.Stock;
import ps.springfinalproject.repository.StockRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Transactional
    @Override
    public Stock create(Stock stock) {
        System.out.println("creating stock... " + stock);
        return stockRepository.save(stock);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Stock> findById(long id) {
        return stockRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Stock> findByProductId(long id) {
        return stockRepository.findByProductId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Stock> findByProduct(Product product) {
        return stockRepository.findByProductId(product.getId());
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll() {
        stockRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @Override
    public Stock update(Stock stock) {
        return stockRepository.save(stock);
    }

    @Transactional
    @Override
    public void delete(Stock stock) {
        stockRepository.delete(stock);
    }
}
