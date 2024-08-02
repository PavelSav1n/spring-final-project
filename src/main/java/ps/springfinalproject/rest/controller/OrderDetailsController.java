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
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.rest.dto.OrderDetailsDto;
import ps.springfinalproject.rest.dto.OrderDto;
import ps.springfinalproject.rest.dto.ProductDto;
import ps.springfinalproject.services.OrderDetailsService;
import ps.springfinalproject.services.OrderService;
import ps.springfinalproject.services.ProductService;
import ps.springfinalproject.services.UserService;

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

            System.out.println("orderDetailsDto = " + orderDetailsDto);
            // Setting info DTO fields without checking isPresent() in BD. Might be issues.
            if (!orderDetailsDto.getProductId().isEmpty()) {
                System.out.println("prod");
                orderDetailsDto.setProductName(productService.findById(Long.parseLong(orderDetailsDto.getProductId())).get().getName());
            }
            if (!orderDetailsDto.getOrderId().isEmpty()) {
                System.out.println("ordID");
                orderDetailsDto.setUserName(orderService.findById(Long.parseLong(orderDetailsDto.getOrderId())).get().getUser().getName());
            }


            model.addAttribute("orderDetailsDtoList", orderDetailsService.findAll().stream().map(OrderDetailsDto::toDto).toList());
            model.addAttribute("orderDtoList", orderService.findAll().stream().map(OrderDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());


            return "add-order-details-page";
        }
        OrderDetails orderDetailsToBeSaved = OrderDetailsDto.fromDto(orderDetailsDto);
        orderDetailsToBeSaved.setProduct(productService.findById(orderDetailsToBeSaved.getProduct().getId()).get()); // setting product without isPresent check
        orderDetailsService.create(orderDetailsToBeSaved);
        return "redirect:/order-details";
    }


    @GetMapping("/order-details/{id}")
    public String getOrderDetailsPage(@PathVariable long id, Model model) {
        Optional<OrderDetails> orderDetailsFromBD = orderDetailsService.findById(id);
        System.out.println("orderDetailsFromBD.get() = " + orderDetailsFromBD.get());
        orderDetailsFromBD.ifPresent(orderDetails -> model.addAttribute("orderDetailsDto", OrderDetailsDto.toDto(orderDetails)));
        return "get-order-details-page";
    }

    @GetMapping("/order-details/{id}/edit")
    public String editOrderDetailsPage(@PathVariable long id, Model model) {
        Optional<OrderDetails> orderDetailsFromBD = orderDetailsService.findById(id);
        if (orderDetailsFromBD.isPresent()) {
            OrderDetailsDto orderDetailsDto = OrderDetailsDto.toDto(orderDetailsFromBD.get());
            orderDetailsDto.setUserName(orderService.findById(orderDetailsFromBD.get().getOrder().getId()).get().getUser().getName()); // setting userName to orderDetails DTO
            model.addAttribute("orderDetailsDto", orderDetailsDto);

            System.out.println("orderDetailsDto = " + orderDetailsDto); // sending fully set orderDetails DTO

            // sending only details from this order:
            model.addAttribute("orderDetailsDtoList", orderDetailsService.findAllByOrderId(orderDetailsFromBD.get().getOrder().getId()).stream().map(OrderDetailsDto::toDto).toList());
            model.addAttribute("orderDtoList", orderService.findAll().stream().map(OrderDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());

            return "edit-order-details-page";
        }
        return "404";
    }

    @PostMapping("/order-details/{id}/edit")
    public String postEditOrderDetailsPage(@Valid OrderDetailsDto orderDetailsDto, BindingResult result, Model model) {
        System.out.println("POSTorderDetailsDto = " + orderDetailsDto);
        // if we don't have input tags in View for all our DTO fields they will return as null here
        // So we must add those fields in View in order to preserve them.
        if (result.hasErrors()) {
            model.addAttribute("orderDetailsDtoList", orderDetailsService.findAll().stream().map(OrderDetailsDto::toDto).toList());
            model.addAttribute("orderDtoList", orderService.findAll().stream().map(OrderDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "edit-order-details-page";
        }

        model.addAttribute("orderDetailsDtoList", orderDetailsService.findAll().stream().map(OrderDetailsDto::toDto).toList());
        OrderDetails orderDetailsToBeSaved = OrderDetailsDto.fromDto(orderDetailsDto);
        Product productFromDB = productService.findById(orderDetailsToBeSaved.getProduct().getId()).get();
        orderDetailsToBeSaved.setProduct(productFromDB); // setting Product from persist
        orderDetailsToBeSaved.setPrice(productFromDB.getPrice() * orderDetailsToBeSaved.getAmount()); // setting correct price according to product price and amount in orderDetails

        orderDetailsService.update(orderDetailsToBeSaved);

        return "redirect:/order-details";
    }

    // Deleting just by ID in URL path
    @GetMapping("order-details/{id}/delete")
    public String deleteOrderDetails(@PathVariable long id) {
        if (orderDetailsService.findById(id).isPresent()) {
            orderDetailsService.deleteById(id);
        }
        return "redirect:/order-details";
    }

}
