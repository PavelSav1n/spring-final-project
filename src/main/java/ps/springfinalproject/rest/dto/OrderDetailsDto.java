package ps.springfinalproject.rest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ps.springfinalproject.domain.OrderDetails;
import ps.springfinalproject.domain.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private String id;
    @NotBlank(message = "You must choose an order")
    private String orderId;
    private String productId;
    private String productName;
    @Positive(message="Amount must be positive")
    @Digits(integer = 5, fraction = 0, message="Amount must be an integer value, not more than 5 digit long")
    private String amount;
    @NotBlank(message = "Enter price")
    @DecimalMin(value = "1.00", message = "Price must be decimal format. For example: '10.55'")
    private String price;

    public static OrderDetailsDto toDto(OrderDetails orderDetails) {
        String id = String.valueOf(orderDetails.getId());
        String orderId = String.valueOf(orderDetails.getOrderId());
        String productId = String.valueOf(orderDetails.getProduct().getId());
        String productName = orderDetails.getProduct().getName();
        String amount = String.valueOf(orderDetails.getAmount());
        String price = String.valueOf(orderDetails.getPrice());

        return new OrderDetailsDto(id, orderId, productId, productName, amount, price);
    }

    public static OrderDetails fromDto(OrderDetailsDto orderDetailsDto) {
        if (orderDetailsDto.id == null){
            orderDetailsDto.id = "0";
        }
        long id = Long.parseLong(orderDetailsDto.id);
        long orderId = Long.parseLong(orderDetailsDto.orderId);
        int amount = Integer.parseInt(orderDetailsDto.amount);
        double price = Double.parseDouble(orderDetailsDto.price);

        return new OrderDetails(id, orderId, new Product(Long.parseLong(orderDetailsDto.productId), orderDetailsDto.productName), amount, price);
    }
}
