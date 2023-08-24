package ps.springfinalproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ps.springfinalproject.services.OrderDetailsService;

@Entity(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_id")
    private long orderId;

    //todo: разобраться как мапятся OneToMany ManyToOne. Вроде бы OneToMany это List<>
//    @ManyToOne(targetEntity = Order.class, fetch = FetchType.EAGER)
//    @JoinColumn(name = "order_id")
//    private Order order;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private int amount;
    private double price;

    public OrderDetails(long orderId, Product product, int amount, double price) {
        this.orderId = orderId;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

}
