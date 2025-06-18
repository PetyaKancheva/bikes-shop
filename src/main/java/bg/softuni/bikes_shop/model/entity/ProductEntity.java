package bg.softuni.bikes_shop.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "composite_name")
    private String compositeName;
    @Column(name = "description")
    private String description;
    @Column(name = "category",nullable = false)
    private String category;
    @Positive
    @Column(name = "price",nullable = false)
    private BigDecimal price;
    @Column(name = "picture_URL")
    private String pictureURL;

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }


    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductEntity setCategory(String category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public ProductEntity setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
        return this;
    }

    public String getCompositeName() {
        return compositeName;
    }

    public ProductEntity setCompositeName(String compositeName) {
        this.compositeName = compositeName;
        return this;
    }
}
