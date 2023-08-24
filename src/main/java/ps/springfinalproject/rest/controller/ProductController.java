package ps.springfinalproject.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.rest.dto.ProductDto;
import ps.springfinalproject.services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product")
    public String getProductsPage(Model model) {
        List<ProductDto> productDtoList = productService.findAll().stream().map(ProductDto::toDto).toList();
        model.addAttribute("productDtoList", productDtoList);
        return "get-products-page";
    }

    @GetMapping("/product/{id}")
    public String getProductPage(@PathVariable long id, Model model) {
        Optional<Product> productFromBD = productService.findById(id);
        if (productFromBD.isPresent()) {
            model.addAttribute("productDto", ProductDto.toDto(productFromBD.get()));
            return "get-product-page";
        }
        return "404";
    }
}
