package ps.springfinalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ps.springfinalproject.domain.OrderDetails;
import ps.springfinalproject.domain.Product;

@Data
@AllArgsConstructor
public class OrderDetailsDto {
    private String id;
    private String orderId;
    private String productId;
    private String productName;
    private String amount;
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
        long id = Long.parseLong(orderDetailsDto.id);
        long orderId = Long.parseLong(orderDetailsDto.orderId);
        int amount = Integer.parseInt(orderDetailsDto.amount);
        double price = Double.parseDouble(orderDetailsDto.price);

        return new OrderDetails(id, orderId, new Product(Long.parseLong(orderDetailsDto.productId), orderDetailsDto.productName), amount, price);
    }
}
