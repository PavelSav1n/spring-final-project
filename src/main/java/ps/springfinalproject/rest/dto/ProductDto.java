package ps.springfinalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ps.springfinalproject.domain.Category;
import ps.springfinalproject.domain.Product;

@Data
@AllArgsConstructor
public class ProductDto {
    private String id;
    private String name;
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
        long id = Long.parseLong(productDto.id);

        return new Product(id, productDto.name, new Category(Long.parseLong(productDto.getCategoryId()), productDto.getCategoryName()), productDto.getImagePath(), productDto.getDetails());
    }
}
