package ps.springfinalproject.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.domain.Stock;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
    private String id;
    @NotBlank(message = "Product is required.")
    private String productId;
    private String productName;
    @NotBlank(message = "Enter amount")
    @Positive(message="Amount must be positive")
    @Digits(integer = 5, fraction = 0, message="Amount must be an integer value, not more than 5 digit long")
    private String amount;
    @NotBlank(message = "Enter price")
    @DecimalMin(value = "1.00", message = "Price must be decimal format. For example: '10.55'")
    private String price;

    public static StockDto toDto(Stock stock) {
        String id = String.valueOf(stock.getId());
        String productId = String.valueOf(stock.getProduct().getId());
        String productName = stock.getProduct().getName();
        String amount = String.valueOf(stock.getAmount());
        String price = String.valueOf(stock.getPrice());

        return new StockDto(id, productId, productName, amount, price);
    }

    public static Stock fromDto(StockDto stockDto) {
        if (stockDto.id == null) {
            stockDto.id = "0";
        }
        long id = Long.parseLong(stockDto.id);
        int amount = Integer.parseInt(stockDto.amount);
        double price = Double.parseDouble(stockDto.price);

        return new Stock(id, new Product(Long.parseLong(stockDto.productId), stockDto.productName), amount, price);
    }
}
