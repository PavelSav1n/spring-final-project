package ps.springfinalproject.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ps.springfinalproject.domain.Order;
import ps.springfinalproject.domain.User;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {
    private String id;
    private String orderDate;
    private String userId;
    private String userName;
    private List<OrderDetailsDto> orderDetailsDtoList;
    private String cost;
    private String temp;

    public static OrderDto toDto(Order order) {
        String id = String.valueOf(order.getId());
        String userId = String.valueOf(order.getUser().getId());
        String userName = order.getUser().getName();
        List<OrderDetailsDto> orderDetailsDtoList = order.getOrderDetailsList().stream().map(OrderDetailsDto::toDto).toList();
        String cost = String.valueOf(order.getCost());
        String temp = String.valueOf(order.getTemp());

        return new OrderDto(id, order.getOrderDate(), userId, userName, orderDetailsDtoList, cost, temp);
    }

    public static Order fromDto(OrderDto orderDto) {
        if (orderDto.id == null) {
            orderDto.id = "0";
        }
        long id = Long.parseLong(orderDto.id);
        if (orderDto.cost == null) {
            orderDto.cost = "0";
        }
        double cost = Double.parseDouble(orderDto.cost);
        boolean temp = Boolean.parseBoolean(orderDto.temp);

        // TODO: 18.08.2023 Написать нормальный список OrderDetails. Пока не понятно, как мы будем передавать данные.

        return new Order(id, orderDto.orderDate, new User(Long.parseLong(orderDto.userId), orderDto.userName), cost, temp);
    }
}
