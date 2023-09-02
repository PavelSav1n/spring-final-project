package ps.springfinalproject.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
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

    @GetMapping("/category/add")
    public String addCategoryPage(Model model) {
        model.addAttribute("categoryDto", new CategoryDto()); // to get use of th:field
        model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
        return "add-category-page";
    }

    @PostMapping("/category/add")
    public String postAddCategoryPage(@Valid CategoryDto categoryDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
            return "add-category-page";
        }
        categoryService.create(CategoryDto.fromDto(categoryDto));
        return "redirect:/category";
    }

    @GetMapping("category/{id}/edit")
    public String editCategoryPage(@PathVariable("id") long id, Model model) {
        Optional<Category> categoryFromBD = categoryService.findById(id);
        if (categoryFromBD.isPresent()) {
            model.addAttribute("categoryDto", CategoryDto.toDto(categoryFromBD.get()));
            model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
            return "edit-category-page";
        }
        return "404";
    }

    @PostMapping("category/{id}/edit")
    public String postEditCategoryPage(@Valid CategoryDto categoryDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
            return "edit-category-page";
        }
        Optional<Category> categoryFromBD = categoryService.findById(Long.parseLong(categoryDto.getId()));
        if (categoryFromBD.isPresent()) {
            categoryService.update(CategoryDto.fromDto(categoryDto));
            return "redirect:/category";
        }
        return "404";
    }

    @GetMapping("category/{id}/delete")
    public String deleteCategoryPage(@PathVariable("id") long id, Model model) {
        Optional<Category> categoryFromBD = categoryService.findById(id);
        if (categoryFromBD.isPresent()) {
            model.addAttribute("categoryDto", CategoryDto.toDto(categoryFromBD.get()));
            model.addAttribute("categoryDtoList", categoryService.findAll().stream().map(CategoryDto::toDto).toList());
            return "delete-category-page";
        }
        return "404";
    }

    @PostMapping("category/{id}/delete")
    public String postDeleteCategoryPage(CategoryDto categoryDto) {
        Optional<Category> categoryFromBD = categoryService.findById(Long.parseLong(categoryDto.getId()));
        if (categoryFromBD.isPresent()) {
            categoryService.delete(CategoryDto.fromDto(categoryDto));
            return "redirect:/category";
        }
        return "404";
    }
}
