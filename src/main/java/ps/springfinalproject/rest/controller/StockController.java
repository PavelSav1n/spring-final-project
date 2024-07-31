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
        StockDto stockDto = new StockDto();
        System.out.println("stockDto = " + stockDto);
        model.addAttribute("stockDto", stockDto);
        model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
        model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
        return "add-stock-page";
    }

    @PostMapping("stock/add")
    public String postAddStockPage(@Valid StockDto stockDto, BindingResult result, Model model) {
        if (result.hasErrors()) { // Checking basic restriction errors which defined in annotations in DTOs.

            // When we add new stock (or other new entity) there are nulls in fields inside stockDto.
            // After we POST some data this fields becomes populated with these data if there are no errors regarding these fields.
            // But some fields, like names of products, usernames and other data, which we store in SQL in IDs cannot be filled, because they exist only in DTOs.
            // So we must do it explicitly. I won't add this mechanics everywhere for now. Just for example.
            if (stockDto.getProductId() != null){ // checking whether product field is filled
                Optional<Product> productFromBD = productService.findById(Long.parseLong(stockDto.getProductId())); // find this product in BD
                productFromBD.ifPresent(product -> stockDto.setProductName(product.getName())); // setting correct name to DTO field
            }
            System.out.println("POSTstockDto = " + stockDto);
            model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "add-stock-page";
        }
        Optional<Stock> stockFromBD = stockService.findByProductId(Long.parseLong(stockDto.getProductId()));

        if (stockFromBD.isPresent()) { // Checking if this product is already in stock
            stockDto.setProductName(productService.findById(Long.parseLong(stockDto.getProductId())).get().getName());
            result.rejectValue("productId", null, stockDto.getProductName() + " is already in stock.");
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
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "edit-stock-page";
        }
        return "404";
    }

    @PostMapping("stock/{id}/edit")
    public String postEditStockPage(@Valid StockDto stockDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "edit-stock-page";
        }
        Optional<Stock> stockProductDuplicate = stockService.findByProductId(Long.parseLong(stockDto.getProductId()));

        // Ошибка, если сток с таким продуктом есть и этот дубликат не текущий сток.
        if (stockProductDuplicate.isPresent() && stockProductDuplicate.get().getId() != Long.parseLong(stockDto.getId())) { // Checking if this product is already in stock & this product not the current product
            result.rejectValue("productId", null, productService.findById(Long.parseLong(stockDto.getProductId())).get().getName() + " is already in stock.");
            model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "edit-stock-page";
        }
        Optional<Stock> stockFromBD = stockService.findById(Long.parseLong(stockDto.getId()));
        if (stockFromBD.isPresent()) {
            Optional<Product> productFromBD = productService.findById(Long.parseLong(stockDto.getProductId()));
            Stock stockToBeSaved = StockDto.fromDto(stockDto);
            stockToBeSaved.setProduct(productFromBD.get());
            stockService.update(stockToBeSaved);
            return "redirect:/stock";
        }
        return "404";
    }

    @GetMapping("stock/{id}/delete")
    public String deleteStockPage(@PathVariable("id") long id, Model model) {
        Optional<Stock> stockFromBD = stockService.findById(id);
        if (stockFromBD.isPresent()) {
            model.addAttribute("stockDto", StockDto.toDto(stockFromBD.get()));
            System.out.println("StockDto.toDto(stockFromBD.get()) = " + StockDto.toDto(stockFromBD.get()));
            model.addAttribute("stockDtoList", stockService.findAll().stream().map(StockDto::toDto).toList());
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            return "delete-stock-page";
        }
        return "404";
    }

    @PostMapping("stock/{id}/delete")
    public String postDeleteStockPage(StockDto stockDto) {
        System.out.println("stockDto = " + stockDto);
        Optional<Stock> stockFromBD = stockService.findById(Long.parseLong(stockDto.getId()));
        System.out.println("stockFromBD = " + stockFromBD);
        System.out.println("Deleting stock... " + StockDto.fromDto(stockDto)); // debug
        if (stockFromBD.isPresent()) {
            stockService.delete(stockFromBD.get());
            return "redirect:/stock";
        }
        return "404";
    }

}
