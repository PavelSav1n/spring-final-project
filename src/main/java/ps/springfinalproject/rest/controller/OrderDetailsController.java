package ps.springfinalproject.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ps.springfinalproject.domain.OrderDetails;
import ps.springfinalproject.rest.dto.OrderDetailsDto;
import ps.springfinalproject.rest.dto.OrderDto;
import ps.springfinalproject.rest.dto.ProductDto;
import ps.springfinalproject.services.OrderDetailsService;
import ps.springfinalproject.services.OrderService;
import ps.springfinalproject.services.ProductService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderDetailsController {
    private final OrderDetailsService orderDetailsService;
    private final OrderService orderService;
    private final ProductService productService;


    @GetMapping("/order-details")
    public String getAllOrderDetailsPage(Model model) {
        model.addAttribute("orderDetailsDtoList", orderDetailsService.findAll().stream().map(OrderDetailsDto::toDto).toList());
        return "get-order-details-page";
    }

    @GetMapping("/order-details/add")
    public String addOrderDetailsPage(Model model) {
        model.addAttribute("orderDetailsDto", new OrderDetailsDto());
        model.addAttribute("orderDetailsDtoList", orderDetailsService.findAll().stream().map(OrderDetailsDto::toDto).toList());
        model.addAttribute("orderDtoList", orderService.findAll().stream().map(OrderDto::toDto).toList());
        model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
        return "add-order-details-page";
    }

    @PostMapping("/order-details/add")
    public String postAddOrderDetailsPage(@Valid OrderDetailsDto orderDetailsDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("orderDetailsDtoList", orderDetailsService.findAll().stream().map(OrderDetailsDto::toDto).toList());
            model.addAttribute("orderDtoList", orderService.findAll().stream().map(OrderDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "add-order-details-page";
        }
        System.out.println("orderDetailsDto = " + orderDetailsDto);
        OrderDetails orderDetailsToBeSaved = OrderDetailsDto.fromDto(orderDetailsDto);
        System.out.println("orderDetailsToBeSaved = " + orderDetailsToBeSaved);
        orderDetailsToBeSaved.setProduct(productService.findById(orderDetailsToBeSaved.getProduct().getId()).get()); // without isPresent check
        orderDetailsService.create(orderDetailsToBeSaved);
        return "redirect:/order-details";
    }


    @GetMapping("/order-details/{id}")
    public String getOrderDetailsPage(@PathVariable long id, Model model) {
        Optional<OrderDetails> orderDetailsFromBD = orderDetailsService.findById(id);
        orderDetailsFromBD.ifPresent(orderDetails -> model.addAttribute("orderDetailsDto", OrderDetailsDto.toDto(orderDetails)));
        return "get-order-details-page";
    }

    @GetMapping("/order-details/{id}/edit")
    public String editOrderDetailsPage(@PathVariable long id, Model model) {
        Optional<OrderDetails> orderDetailsFromBD = orderDetailsService.findById(id);
        if (orderDetailsFromBD.isPresent()) {
            model.addAttribute("orderDetailsDto", OrderDetailsDto.toDto(orderDetailsFromBD.get()));
            model.addAttribute("orderDtoList", orderService.findAll().stream().map(OrderDto::toDto).toList());
            model.addAttribute("productList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "edit-order-details-page";
        }
        return "404";
    }
}
