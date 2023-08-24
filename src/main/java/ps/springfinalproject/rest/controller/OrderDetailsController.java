package ps.springfinalproject.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ps.springfinalproject.rest.dto.OrderDetailsDto;
import ps.springfinalproject.services.OrderDetailsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderDetailsController {
    private final OrderDetailsService orderDetailsService;

    @GetMapping("/order-details")
    public String getOrderDetailsPage(Model model) {
        List<OrderDetailsDto> orderDetailsDtoList = orderDetailsService.findAll().stream().map(OrderDetailsDto::toDto).toList();
        model.addAttribute("orderDetailsDtoList", orderDetailsDtoList);
        return "get-order-details-page";
    }
}
