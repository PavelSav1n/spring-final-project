package ps.springfinalproject.services;

import ps.springfinalproject.domain.Product;
import ps.springfinalproject.domain.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {
    //crud

    Stock create(Stock stock);

    Optional<Stock> findById(long id);

    Optional<Stock> findByProductId(long id);

    Optional<Stock> findByProduct(Product product);

    List<Stock> findAll();

    void printAll();

    Stock update(Stock stock);

    void delete(Stock stock);
}
