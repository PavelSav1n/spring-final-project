package ps.springfinalproject.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ps.springfinalproject.domain.Category;
import ps.springfinalproject.domain.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    @NotBlank(message = "Product name is required.")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters.")
    private String name;
    @NotBlank(message = "Category is required.")
    private String categoryId;
    private String categoryName;
    private String imagePath;
    private String details;

    public static ProductDto toDto(Product product) {
        String id = String.valueOf(product.getId());
        String categoryId = String.valueOf(product.getCategory().getId());
        String categoryName = product.getCategory().getName();

        return new ProductDto(id, product.getName(), categoryId, categoryName, product.getImagePath(), product.getDetails());
    }

    public static Product fromDto(ProductDto productDto) {
        if (productDto.id == null) {
            productDto.id = "0";
        }
        long id = Long.parseLong(productDto.id);

        System.out.println("PRODUCT : fromDto");
        System.out.println("productDto.categoryId = " + productDto.categoryId);
        System.out.println("productDto.categoryName = " + productDto.categoryName);

        return new Product(
                id,
                productDto.name,
                new Category(Long.parseLong(productDto.categoryId), productDto.categoryName),
                productDto.imagePath,
                productDto.details);
    }
}
