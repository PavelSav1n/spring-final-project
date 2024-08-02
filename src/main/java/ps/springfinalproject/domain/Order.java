package ps.springfinalproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "orderDetailsList") // Lombok @ToString generation causing cyclic references. So we exclude these bidirectional relationship between fields to avoid it.
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
    //    @JoinColumn(name = "order_id")
    // mappedBy = "order" indicates that the OrderDetails entity owns the relationship, and the 'order' field in OrderDetails maps this relationship.
    @OneToMany(targetEntity = OrderDetails.class, mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetailsList;

    private double cost;

    private boolean temp;

    // Constructor with orderDetails list (cannot be created in persist before orderDetails, which requires order.id from persist).
    public Order(User user, List<OrderDetails> orderDetailsList, double cost) {
        this.user = user;
        this.orderDetailsList = orderDetailsList;
        this.cost = cost;
    }

    // Constructor for persist like (INSERT INTO orders (user_id, cost) VALUES ('2', '430.65');).
    public Order(User user, double cost) {
        this.user = user;
        this.cost = cost;
    }

    // Constructor for DTO:
    public Order(long id) {
        this.id = id;
    }

    // Constructor for creating Temp Order in persist:
    public Order(User user, boolean temp) {
        this.user = user;
        this.temp = temp;
    }

    // Constructor for UPDATE
    public Order(long id, String orderDate, User user, double cost) {
        this.id = id;
        this.orderDate = orderDate;
        this.user = user;
        this.cost = cost;
    }


    public Order(long id, User user, List<OrderDetails> orderDetailsList, double cost) {
        this.id = id;
        this.user = user;
        this.orderDetailsList = orderDetailsList;
        this.cost = cost;
    }


}
