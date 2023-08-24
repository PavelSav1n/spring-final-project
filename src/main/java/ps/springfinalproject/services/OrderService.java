package ps.springfinalproject.services;

import ps.springfinalproject.domain.Order;
import ps.springfinalproject.domain.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    //crud

    Order create(Order order);

    Optional<Order> findById(long id);

    List<Order> findAllByUser(User user);

    List<Order> findAll();

    void printAll();

    Order update(Order order);

    void delete(Order order);

}
