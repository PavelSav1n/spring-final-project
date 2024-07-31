package ps.springfinalproject.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ps.springfinalproject.domain.Category;
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.domain.Stock;
import ps.springfinalproject.rest.controller.StockController;
import ps.springfinalproject.services.OrderService;
import ps.springfinalproject.services.OrderServiceImpl;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    @NotBlank(message = "Product name is required.")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters.")
    private String name;
    @DecimalMin(value = "0.00", message = "Price must be decimal format. For example: '10.55'")
    private String price;
    @Positive(message="Amount must be positive")
    @Digits(integer = 5, fraction = 0, message="Amount must be an integer value, not more than 5 digit long")
    private String amountToOrder;
    private String amountInStock;
    @NotBlank(message = "Category is required.")
    private String categoryId;
    private String categoryName;
    private String imagePath;
    private String details;

    public static ProductDto toDto(Product product) {
        String id = String.valueOf(product.getId());
        String price = String.valueOf(product.getPrice());
        String amountToOrder = "0"; // TODO: trying to fill these fields through ProductController
        String amountInStock = "0";
        String categoryId = String.valueOf(product.getCategory().getId());
        String categoryName = product.getCategory().getName();

        return new ProductDto(
                id,
                product.getName(),
                price,
                amountToOrder,
                amountInStock,
                categoryId,
                categoryName,
                product.getImagePath(),
                product.getDetails());
    }

    public static Product fromDto(ProductDto productDto) {
        if (productDto.id == null) {
            productDto.id = "0";
        }
        long id = Long.parseLong(productDto.id);

        if (productDto.price == null) {
            productDto.price = "0";
        }
        double price = Double.parseDouble(productDto.price);
        System.out.println("PRODUCT : fromDto");
        System.out.println("productDto.categoryId = " + productDto.categoryId);
        System.out.println("productDto.categoryName = " + productDto.categoryName);
        System.out.println("price = " + price);

        return new Product(
                id,
                productDto.name,
                price,
                new Category(Long.parseLong(productDto.categoryId), productDto.categoryName),
                productDto.imagePath,
                productDto.details);
    }
}
