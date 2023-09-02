package ps.springfinalproject.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.domain.Stock;
import ps.springfinalproject.rest.dto.ProductDto;
import ps.springfinalproject.rest.dto.StockDto;
import ps.springfinalproject.services.ProductService;
import ps.springfinalproject.services.StockService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;
    private final ProductService productService;

    @GetMapping("/stock")
    public String getStockPage(Model model) {
        model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
        return "get-stock-page";
    }

    @GetMapping("stock/add")
    public String addStockPage(Model model) {
        model.addAttribute("stockDto", new StockDto());
        model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
        model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
        return "add-stock-page";
    }

    @PostMapping("stock/add")
    public String postAddStockPage(@Valid StockDto stockDto, BindingResult result, Model model) {

        if (result.hasErrors()) { // Checking basic restriction errors
            model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "add-stock-page";
        }
        Optional<Stock> stockFromBD = stockService.findByProductId(Long.parseLong(stockDto.getProductId()));

        if (stockFromBD.isPresent()) { // Checking if this product is already in stock
            result.rejectValue("productId", null, "This product is already in stock.");
            model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "add-stock-page";
        }
        Optional<Product> productFromBD = productService.findById(Long.parseLong(stockDto.getProductId()));
        if (productFromBD.isPresent()) {
            stockDto.setProductName(productFromBD.get().getName()); // Completing stockDto to create correct fromDto operation.
            stockService.create(StockDto.fromDto(stockDto));
            return "redirect:/stock";
        }
        return "404";
    }

    @GetMapping("stock/{id}/edit")
    public String editStockPage(@PathVariable("id") long id, Model model) {
        Optional<Stock> stockFromBD = stockService.findById(id);
        if (stockFromBD.isPresent()) {
            model.addAttribute("stockDto", StockDto.toDto(stockFromBD.get()));
            model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
            return "edit-stock-page";
        }
        return "404";
    }

    @PostMapping("stock/{id}/edit")
    public String postEditStockPage(@Valid StockDto stockDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
            return "edit-stock-page";
        }
        Optional<Stock> stockFromBD = stockService.findById(Long.parseLong(stockDto.getId()));
        if (stockFromBD.isPresent()) {
            stockService.update(StockDto.fromDto(stockDto));
            return "redirect:/stock";
        }
        return "404";
    }


}
