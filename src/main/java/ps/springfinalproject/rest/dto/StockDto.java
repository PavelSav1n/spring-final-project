package ps.springfinalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ps.springfinalproject.domain.Product;
import ps.springfinalproject.domain.Stock;

@Data
@AllArgsConstructor
public class StockDto {
    private String id;
    private String productId;
    private String productName;
    private String amount;
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
        long id = Long.parseLong(stockDto.id);
        int amount = Integer.parseInt(stockDto.amount);
        double price = Double.parseDouble(stockDto.price);

        return new Stock(id, new Product(Long.parseLong(stockDto.productId), stockDto.productName), amount, price );
    }
}
