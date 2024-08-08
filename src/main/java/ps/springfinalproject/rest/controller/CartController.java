package ps.springfinalproject.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ps.springfinalproject.domain.*;
import ps.springfinalproject.rest.dto.OrderDetailsDto;
import ps.springfinalproject.rest.dto.OrderDto;
import ps.springfinalproject.rest.dto.ProductDto;
import ps.springfinalproject.services.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final UserService userService;
    private final OrderService orderService;
    private final OrderDetailsService orderDetailsService;
    private final ProductService productService;
    private final StockService stockService;


    //TODO:*********************************************************************************************
    // Well I don't know how to do it properly so here we will catch amountToOrder and will create order.
    // 1. Creating a temp order with a default User
    // 2. Creating a temp orderDetail
    // 3. Put there Product and amountToOrder

    // Checkout is simple for now:
    // 1. We reevaluate stocks. If there is not enough stock, just redirect to /order with message in console.
    // 2. Find temp order in DB for this user, setting its temp flag to false.
    // 3. Updating order in DB
    @GetMapping("/cart/checkout")
    public String postCartCheckout(Model model) {
        // DefaultUser while we don't have any authorisation:
        User defaultUser = userService.findById(2L).get();
        Optional<Order> tempOrderFromDB = orderService.findTempByUser(defaultUser);
        if (tempOrderFromDB.isPresent()) {
            for (OrderDetails orderDetails : tempOrderFromDB.get().getOrderDetailsList()) {
                Stock stock = stockService.findByProductId(orderDetails.getProduct().getId()).get();
                if ((stock.getAmount() - orderDetails.getAmount()) < 0) {
                    System.out.println("NOT ENOUGH STOCK OF '" + stock.getProduct().getName() + "' WHILE CHECKING OUT");
                    return "redirect:/order";
                }
                stock.setAmount(stock.getAmount() - orderDetails.getAmount());
            }
            tempOrderFromDB.get().setTemp(false);
            orderService.update(tempOrderFromDB.get());
            return "redirect:/order";
        }
        return "redirect:/";
    }

    @PostMapping("/product/{id}")
    public String postGetProductPage(@PathVariable long id, @Valid ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) { // Positive amounts a verified through @Annotations in DTO
            cartModule(model, userService, orderDetailsService, orderService);
            return "get-product-page";
        }

        if (Integer.parseInt(productDto.getAmountToOrder()) > stockService.findByProductId(Long.parseLong((productDto.getId()))).get().getAmount()) {
            result.rejectValue("amountToOrder", null, "There is not enough '" + productDto.getName() + "' in stock. Please check available amount");
            cartModule(model, userService, orderDetailsService, orderService);
            return "get-product-page";
        }

        // DefaultUser while we don't have any authorisation:
        User defaultUser = userService.findById(2L).get();

        // We need just to add into a list new OrderDetails rows.
        // So that we keep previous data until order becomes permanent.
        // We will set permanent state to order somewhere else.

        // There are two case scenarios:
        Optional<Order> tempOrderFromDB = orderService.findTempByUser(defaultUser);

        // 1. There is an order in DB already
        if (tempOrderFromDB.isPresent()) {

            List<OrderDetails> tempOrderDetailsListFromDB = orderDetailsService.findAllByOrderId(tempOrderFromDB.get().getId());
            Optional<Product> productFromDB = productService.findById(Long.parseLong(productDto.getId()));


            int amount = Integer.parseInt(productDto.getAmountToOrder());
            double price = productFromDB.get().getPrice() * amount;

            OrderDetails tempOrderDetails = new OrderDetails(tempOrderFromDB.get(), productFromDB.get(), amount, price);
            OrderDetails tempOrderDetailsFromDB = orderDetailsService.create(tempOrderDetails);

            // Sum of all OD elems prices
            for (OrderDetails elem : tempOrderDetailsListFromDB) {
                price += elem.getPrice();
            }
            tempOrderDetailsListFromDB.add(tempOrderDetailsFromDB); // adding OD to ODList
            tempOrderFromDB.get().setOrderDetailsList(tempOrderDetailsListFromDB); // setting ODList to Order
            tempOrderFromDB.get().setCost(price);

            orderService.update(tempOrderFromDB.get());

        } else {
            // 2. There is no order in DB yet

            Order tempOrderToBeCreated = new Order(defaultUser, true); // Setting temp flag in constructor
            Order newTempOrderFromDB = orderService.create(tempOrderToBeCreated); // getting a new temp order with ID
            Optional<Product> productFromDB = productService.findById(Long.parseLong(productDto.getId())); // finding product in DB

            int amount = Integer.parseInt(productDto.getAmountToOrder());
            double price = productFromDB.get().getPrice() * amount;

            OrderDetails tempOrderDetails = new OrderDetails(newTempOrderFromDB, productFromDB.get(), amount, price);
            OrderDetails tempOrderDetailsFromDB = orderDetailsService.create(tempOrderDetails);

            List<OrderDetails> orderDetailsList = new ArrayList<>();
            orderDetailsList.add(tempOrderDetailsFromDB);

            newTempOrderFromDB.setOrderDetailsList(orderDetailsList);
            newTempOrderFromDB.setCost(price);
            newTempOrderFromDB.setOrderDate(String.valueOf(new Timestamp(System.currentTimeMillis())));

            orderService.create(newTempOrderFromDB);


        }
        return "redirect:/product";
    }

    // This is a cart module.
    // Passing to View orderDetailsDtoList and orderDto
    // DefaultUser while we don't have any authorisation -- user_id = 2L
    static void cartModule(Model model, UserService userService, OrderDetailsService orderDetailsService, OrderService orderService) {
        User defaultUser = userService.findById(2L).get();
        Optional<Order> tempOrderFromDB = orderService.findTempByUser(defaultUser);
        if (tempOrderFromDB.isPresent()) {
            model.addAttribute("orderDetailsDtoList", orderDetailsService.findAllByOrderId(tempOrderFromDB.get().getId()).stream().map(OrderDetailsDto::toDto).toList());
            model.addAttribute("orderDto", OrderDto.toDto(tempOrderFromDB.get()));
        }
    }
}
