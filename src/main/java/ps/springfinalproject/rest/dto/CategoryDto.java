package ps.springfinalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ps.springfinalproject.domain.Category;

@Data
@AllArgsConstructor
public class CategoryDto {
    private String id;
    private String name;

    public static CategoryDto toDto(Category category) {
        String id = String.valueOf(category.getId());

        return new CategoryDto(id, category.getName());
    }

    public static Category fromDto(CategoryDto categoryDto) {
        long id = Long.parseLong(categoryDto.id);

        return new Category(id, categoryDto.getName());
    }
}
