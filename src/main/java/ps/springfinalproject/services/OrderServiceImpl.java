package ps.springfinalproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.springfinalproject.domain.Order;
import ps.springfinalproject.domain.User;
import ps.springfinalproject.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Order> findTempByUser(User user) {
        return orderRepository.findByUserAndTemp(user, true);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll() {
        orderRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }
}
