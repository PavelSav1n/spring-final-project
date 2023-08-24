package ps.springfinalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.springfinalproject.domain.Stock;

import java.util.Optional;


public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductId(long id);
}
