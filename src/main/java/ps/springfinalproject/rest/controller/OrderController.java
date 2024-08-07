package ps.springfinalproject.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ps.springfinalproject.domain.Order;
import ps.springfinalproject.domain.OrderDetails;
import ps.springfinalproject.domain.User;
import ps.springfinalproject.rest.dto.OrderDto;
import ps.springfinalproject.rest.dto.UserDto;
import ps.springfinalproject.services.OrderDetailsService;
import ps.springfinalproject.services.OrderService;
import ps.springfinalproject.services.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderDetailsService orderDetailsService;

    @GetMapping("/order")
    public String getAllOrdersPage(Model model) {
        model.addAttribute("orderDtoList", orderService.findAll().stream().map(OrderDto::toDto).toList());
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

    @GetMapping("/order/{id}/edit")
    public String editOrderPage(@PathVariable long id, Model model) {
        Optional<Order> orderFromBD = orderService.findById(id);
        if (orderFromBD.isPresent()) {
            model.addAttribute("orderDto", OrderDto.toDto(orderFromBD.get()));

            System.out.println(OrderDto.toDto(orderFromBD.get()).getOrderDate());

            model.addAttribute("userDtoList", userService.findAll().stream().map(UserDto::toDto).toList());
            model.addAttribute("orderDtoList", orderService.findAll().stream().map(OrderDto::toDto).toList());
            return "edit-order-page";
        }
        return "404";
    }

    @PostMapping("/order/{id}/edit")
    public String postEditOrderPage(@Valid OrderDto orderDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userDtoList", userService.findAll().stream().map(UserDto::toDto).toList());
            model.addAttribute("orderDtoList", orderService.findAll().stream().map(OrderDto::toDto).toList());
            return "edit-order-page";
        }

        Optional<Order> orderFromBD = orderService.findById(Long.parseLong(orderDto.getId()));
        if (orderFromBD.isPresent()) {
            Order orderToBeUpdated = OrderDto.fromDto(orderDto);

            // We need to get user from persist. Now we have only user's id and name. We need to get other fields.
            Optional<User> userFromPersist = userService.findById(orderToBeUpdated.getUser().getId());
            userFromPersist.ifPresent(orderToBeUpdated::setUser);
            // Also need to set orderDetails. Now it's just from orderDetails table.
            orderToBeUpdated.setOrderDetailsList(orderDetailsService.findAllByOrderId(orderToBeUpdated.getId()));
            // Setting cost and temp flag
            Optional<Order> orderFromDB = orderService.findById(orderToBeUpdated.getId());
            orderToBeUpdated.setCost(orderFromDB.get().getCost());

            orderService.update(orderToBeUpdated);
            return "redirect:/order";
        }
        return "404";
    }

    // Deleting order with all its orderDetails
    @GetMapping("/order/{id}/delete")
    public String deleteOrder(@PathVariable long id) {
        Optional<Order> orderToBeDeleted = orderService.findById(id);
        if (orderToBeDeleted.isPresent()) {
            List<OrderDetails> orderDetailsListToBeDeleted = orderDetailsService.findAllByOrderId(id);
            for (OrderDetails orderDetails : orderDetailsListToBeDeleted) {
                orderDetailsService.delete(orderDetails);
            }
            orderService.delete(orderToBeDeleted.get());
        }
        return "redirect:/order";
    }

}
