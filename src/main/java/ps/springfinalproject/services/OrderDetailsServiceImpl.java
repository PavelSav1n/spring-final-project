package ps.springfinalproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.springfinalproject.domain.OrderDetails;
import ps.springfinalproject.repository.OrderDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private final OrderDetailsRepository orderDetailsRepository;

    @Transactional
    @Override
    public OrderDetails create(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<OrderDetails> findById(long id) {
        return orderDetailsRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDetails> findAllByOrderId(long orderId) {
        return orderDetailsRepository.findAllByOrderId(orderId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDetails> findAll() {
        return orderDetailsRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public void printAllByOrderId(long orderId) {
        orderDetailsRepository.findAllByOrderId(orderId).forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll() {
        orderDetailsRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    @Override
    public OrderDetails update(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Transactional
    @Override
    public void delete(OrderDetails orderDetails) {
        orderDetailsRepository.delete(orderDetails);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        orderDetailsRepository.deleteById(id);
    }


}
