package ps.springfinalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.springfinalproject.domain.OrderDetails;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findAllByOrderId(long orderId);

}
