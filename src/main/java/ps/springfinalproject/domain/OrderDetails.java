package ps.springfinalproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "order")  // Lombok @ToString generation causing cyclic references. So we exclude these interdependent fields to avoid it.
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(name = "order_id")
//    @ManyToOne(targetEntity = Order.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    // Could not use 'long id' for some reason. Thymeleaf could not correctly map these fields.
    private Order order;



    //todo: разобраться как мапятся OneToMany ManyToOne. Вроде бы OneToMany это List<>
//    @ManyToOne(targetEntity = Order.class, fetch = FetchType.EAGER)
//    @JoinColumn(name = "order_id")
//    private Order order;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private int amount;
    private double price;

    public OrderDetails(Order order, Product product, @Positive(message = "Amount must be positive") @Digits(integer = 5, fraction = 0, message = "Amount must be an integer value, not more than 5 digit long") int amount, double price) {
        this.order = order;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    // Excluding bidirectional referencing, just showing order.id:
    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", orderId=" + order.getId() +
                ", product=" + product +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
