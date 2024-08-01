package ps.springfinalproject.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ps.springfinalproject.domain.Order;
import ps.springfinalproject.domain.OrderDetails;
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.domain.User;
import ps.springfinalproject.rest.dto.OrderDto;
import ps.springfinalproject.rest.dto.ProductDto;
import ps.springfinalproject.rest.dto.UserDto;
import ps.springfinalproject.services.OrderDetailsService;
import ps.springfinalproject.services.OrderService;
import ps.springfinalproject.services.ProductService;
import ps.springfinalproject.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderDetailsService orderDetailsService;
    private final ProductService productService;
    ArrayList<OrderDetails> orderDetailsFromOrderPersist = new ArrayList<>();

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

    @GetMapping("/order/{id}/edit")
    public String editOrderPage(@PathVariable long id, Model model) {
        Optional<Order> orderFromBD = orderService.findById(id);
        if (orderFromBD.isPresent()) {
            model.addAttribute("orderDto", OrderDto.toDto(orderFromBD.get()));
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
            // We need to get user from persist. Now we have only user's id and name. We need to get other fields.
            Order orderToBeUpdated = OrderDto.fromDto(orderDto);
            Optional<User> userFromPersist = userService.findById(orderToBeUpdated.getUser().getId());
            userFromPersist.ifPresent(orderToBeUpdated::setUser);
            // Also need to set orderDetails. Now it's just from orderDetails table.
            orderToBeUpdated.setOrderDetails(orderDetailsService.findAllByOrderId(orderToBeUpdated.getId()));
            orderService.update(orderToBeUpdated);
            return "redirect:/order";
        }
        return "404";
    }

    //TODO:*********************************************************************************************
    // Well I don't know how to do it properly so here we will catch amountToOrder and will create order.
    // 1. Creating a temp order with a default User
    // 2. Creating a temp orderDetail
    // 3. Put there this Product and amountToOrder

    @PostMapping("/product/{id}")
    public String postGetProductPage(@PathVariable long id, @Valid ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("error");
        }

        // DefaultUser while we don't have any authorisation:
        User defaultUser = userService.findById(2L).get();
        Order tempOrderToBeCreated = new Order(defaultUser, true);
        Order tempOrderFromPersist = orderService.create(tempOrderToBeCreated); // getting order with ID
        Optional<Product> productFromBD = productService.findById(Long.parseLong(productDto.getId()));

        int amount = Integer.parseInt(productDto.getAmountToOrder());
        double price = productFromBD.get().getPrice() * amount;
        OrderDetails tempOrderDetails = new OrderDetails(tempOrderFromPersist.getId(), productService.findById(Long.parseLong(productDto.getId())).get(), amount, price);

        OrderDetails tempOrderDetailsFromPersist = orderDetailsService.create(tempOrderDetails);

        System.out.println("tempOrderDetailsFromPersist = " + tempOrderDetailsFromPersist);
        System.out.println("amount = " + amount);
        System.out.println("price = " + price);
        System.out.println("tempOrderDetails = " + tempOrderDetails);

        // We need just to add into a list new OrderDetails rows.
        // So that we keep previous data until order becomes permanent.


        System.out.println("productDto = " + productDto);

        return "get-product-page";


    }
}
