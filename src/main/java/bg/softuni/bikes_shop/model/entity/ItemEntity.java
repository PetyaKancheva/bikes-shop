package bg.softuni.bikes_shop.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="items")
public class ItemEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    private ProductEntity product;
    @Column(name="quantity",nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name="order_id",nullable = false)
    private OrderEntity order;

    public ProductEntity getProduct() {
        return product;
    }

    public ItemEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ItemEntity setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public ItemEntity setOrder(OrderEntity order) {
        this.order = order;
        return this;
    }
}
