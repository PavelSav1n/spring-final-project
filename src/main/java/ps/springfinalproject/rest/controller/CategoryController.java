package ps.springfinalproject.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ps.springfinalproject.domain.Category;
import ps.springfinalproject.rest.dto.CategoryDto;
import ps.springfinalproject.services.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category")
    public String getCategoriesPage(Model model) {
        List<CategoryDto> categoryDtoList = categoryService.findAll().stream().map(CategoryDto::toDto).toList();
        model.addAttribute("categoryDtoList", categoryDtoList);
        return "get-categories-page";
    }

    @GetMapping("/category/{id}")
    public String getCategoryPage(@PathVariable long id, Model model) {
        Optional<Category> categoryFromBD = categoryService.findById(id);
        if (categoryFromBD.isPresent()) {
            model.addAttribute("categoryDto", CategoryDto.toDto(categoryFromBD.get()));
            return "get-category-page";
        }
        return "404";
    }
}
