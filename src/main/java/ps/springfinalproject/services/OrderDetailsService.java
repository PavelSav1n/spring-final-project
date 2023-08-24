package ps.springfinalproject.services;

import org.aspectj.weaver.ast.Or;
import ps.springfinalproject.domain.OrderDetails;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {
    //crud
    OrderDetails create(OrderDetails orderDetails);

    Optional<OrderDetails> findById(long id);

    List<OrderDetails> findAllByOrderId(long orderId);

    List<OrderDetails> findAll();

    void printAllByOrderId(long orderId);

    void printAll();

    OrderDetails update(OrderDetails orderDetails);

    void delete(OrderDetails orderDetails);
}
