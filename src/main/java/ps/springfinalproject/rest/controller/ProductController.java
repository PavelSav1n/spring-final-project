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
import ps.springfinalproject.rest.dto.CategoryDto;
import ps.springfinalproject.rest.dto.ProductDto;
import ps.springfinalproject.services.CategoryService;
import ps.springfinalproject.services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

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

    @GetMapping("/product/add")
    public String addProductPage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
        model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
        return "add-product-page";
    }

    @PostMapping("/product/add")
    public String postAddProductPage(@Valid ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productDtoList", productService.findAll().stream().map(ProductDto::toDto).toList());
            model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
            return "add-product-page";
        }
        productService.create(ProductDto.fromDto(productDto));
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
        if (result.hasErrors()) {
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
