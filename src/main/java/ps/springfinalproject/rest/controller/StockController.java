package ps.springfinalproject.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ps.springfinalproject.rest.dto.StockDto;
import ps.springfinalproject.services.StockService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/stock")
    public String getStockPage(Model model) {
        List<StockDto> stockDtoList = stockService.findAll().stream().map(StockDto::toDto).toList();
        model.addAttribute("stockDtoList", stockDtoList);
        return "get-stock-page";
    }
}
