package ps.springfinalproject.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ps.springfinalproject.domain.Order;
import ps.springfinalproject.rest.dto.OrderDto;
import ps.springfinalproject.services.OrderService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order")
    public String getAllOrdersPage(Model model) {
        List<OrderDto> orderDtoList = orderService.findAll().stream().map(OrderDto::toDto).toList();
        model.addAttribute("orderDtoList", orderDtoList);
        return "get-orders-page";
    }

    @GetMapping("/order/{id}")
    public String getOrderPage(@PathVariable long id, Model model) {
        Optional<Order> orderFromBD = orderService.findById(id);
        if (orderFromBD.isPresent()) {
            model.addAttribute("orderDto", OrderDto.toDto(orderFromBD.get()));
            return "get-order-page";
        }
        return "404";
    }
}
