package ps.springfinalproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToOne(targetEntity = Category.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;

    //todo: Нужно проверить, получать ли путь к картинке или получать саму картинку из формы html
    @Column(name = "image_path")
    private String imagePath;

    private String details;

    public Product(String name, Category category, String imagePath, String details) {
        this.name = name;
        this.category = category;
        this.imagePath = imagePath;
        this.details = details;
    }
    // For Dto dependencies:
    public Product(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
