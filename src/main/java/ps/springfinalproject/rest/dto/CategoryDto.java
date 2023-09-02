package ps.springfinalproject.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ps.springfinalproject.domain.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String id;
    @NotBlank(message = "Category name is required.")
    @Size(min = 3, max = 50, message = "Category name must be between 2 and 50 characters.")
    private String name;

    public static CategoryDto toDto(Category category) {
        String id = String.valueOf(category.getId());

        return new CategoryDto(id, category.getName());
    }

    public static Category fromDto(CategoryDto categoryDto) {
        if (categoryDto.id == null) {
            categoryDto.id = "0";
        }
        long id = Long.parseLong(categoryDto.id);

        return new Category(id, categoryDto.getName());
    }
}
