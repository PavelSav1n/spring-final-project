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
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.domain.Stock;
import ps.springfinalproject.domain.User;
import ps.springfinalproject.rest.dto.CategoryDto;
import ps.springfinalproject.rest.dto.OrderDetailsDto;
import ps.springfinalproject.rest.dto.OrderDto;
import ps.springfinalproject.rest.dto.ProductDto;
import ps.springfinalproject.services.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final StockService stockService;
    private final OrderDetailsService orderDetailsService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/product")
    public String getProductsPage(Model model) {
        model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());

        // Adding Cart module to product page:
        // 1. Searching for current temp order and getting its ID
        // 2. Getting list of OD bt ID and temp flag
        // DefaultUser while we don't have any authorisation:
        User defaultUser = userService.findById(2L).get();
        Optional<Order> tempOrderFromDB = orderService.findTempByUser(defaultUser);
        System.out.println("tempOrderFromDB = " + tempOrderFromDB);
        if (tempOrderFromDB.isPresent()) {
            model.addAttribute("orderDetailsDtoList", orderDetailsService.findAllByOrderId(tempOrderFromDB.get().getId()).stream().map(OrderDetailsDto::toDto).toList());
            model.addAttribute("orderDto", OrderDto.toDto(tempOrderFromDB.get()));
        }
        return "get-products-page";
    }

    @GetMapping("/product/{id}")
    public String getProductPage(@PathVariable long id, Model model) {
        Optional<Product> productFromBD = productService.findById(id);
        if (productFromBD.isPresent()) {
            ProductDto productDto = ProductDto.toDto(productFromBD.get());
            // Check, whether this product is in stock:
            if (stockService.findByProductId(id).isPresent())
                productDto.setAmountInStock(String.valueOf(stockService.findByProductId(id).get().getAmount())); // Setting correct stocks for product
            model.addAttribute("productDto", productDto);
            System.out.println("productDto = " + productDto);
            return "get-product-page";
        }
        return "404";
    }


    @GetMapping("/product/add")
    public String addProductPage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
        model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
        return "add-product-page";
    }

    // Adding new product to DB and adding zero stock for it to stock table
    // TODO: remove price from stock. Now it lives at Product domain
    @PostMapping("/product/add")
    public String postAddProductPage(@Valid ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
            return "add-product-page";
        }
        Product product = ProductDto.fromDto(productDto);
        productService.create(product);
        stockService.create(new Stock(productService.findById(product.getId()).get(), 0, 0)); // Creating product will create default stock for it
        return "redirect:/product";
    }

    @GetMapping("/product/{id}/edit")
    public String editProductPage(@PathVariable("id") long id, Model model) {
        Optional<Product> productFromBD = productService.findById(id);
        if (productFromBD.isPresent()) {
            model.addAttribute("productDto", ProductDto.toDto(productFromBD.get()));
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
            return "edit-product-page";
        }
        return "404";
    }

    @PostMapping("product/{id}/edit")
    public String postEditProductPage(@Valid ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) { // errors are checked in dtos via @jakarta validation annotations
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
            return "edit-product-page";
        }
        Optional<Product> productFromBD = productService.findById(Long.parseLong(productDto.getId()));
        if (productFromBD.isPresent()) {
            productService.update(ProductDto.fromDto(productDto));
            return "redirect:/product";
        }
        return "404";
    }

    @GetMapping("product/{id}/delete")
    public String deleteProductPage(@PathVariable("id") long id, Model model) {
        Optional<Product> productFromBD = productService.findById(id);
        if (productFromBD.isPresent()) {
            model.addAttribute("productDto", ProductDto.toDto(productFromBD.get()));
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
            return "delete-product-page";
        }
        return "404";
    }

    @PostMapping("product/{id}/delete")
    public String postDeleteProductPage(ProductDto productDto) {
        Optional<Product> productFromBD = productService.findById(Long.parseLong(productDto.getId()));
        if (productFromBD.isPresent()) {
            // We can delete via ProductDto.fromDto(productDto) or productFromBD.get(). Here just an example.
            productService.delete(ProductDto.fromDto(productDto));
            return "redirect:/product";
        }
        return "404";
    }


}
