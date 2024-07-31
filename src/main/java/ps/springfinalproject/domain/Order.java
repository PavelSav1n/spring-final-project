package ps.springfinalproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_date")
    private String orderDate;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    //    @OneToMany(mappedBy = "order")
    @OneToMany(targetEntity = OrderDetails.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderDetails> orderDetails;

    private double cost;

    // Constructor with orderDetails list (cannot be created in persist before orderDetails, which requires order.id from persist).
    public Order(User user, List<OrderDetails> orderDetails, double cost) {
        this.user = user;
        this.orderDetails = orderDetails;
        this.cost = cost;
    }

    // Constructor for persist like (INSERT INTO orders (user_id, cost) VALUES ('2', '430.65');).
    public Order(User user, double cost) {
        this.user = user;
        this.cost = cost;
    }

    // Constructor for UPDATE
    public Order(long id, String orderDate, User user, double cost) {
        this.id = id;
        this.orderDate = orderDate;
        this.user = user;
        this.cost = cost;
    }


    public Order(long id, User user, List<OrderDetails> orderDetails, double cost) {
        this.id = id;
        this.user = user;
        this.orderDetails = orderDetails;
        this.cost = cost;
    }


}
