package ps.springfinalproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;

    private int amount;
    private double price;

    public Stock(Product product, int amount, double price) {
        this.product = product;
        this.amount = amount;
        this.price = price;
    }
}
