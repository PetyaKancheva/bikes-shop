package bg.softuni.bikes_shop.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name= "orders")
public class OrderEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="buyer_id",nullable = false)
    private UserEntity buyer;
    @OneToMany(mappedBy = "order")
    private List<ItemEntity> items;
    @Column(name="total")
    private BigDecimal totalSum;
    @Column(name="date_created", nullable = false)
    private LocalDate dateCreated;
    @Column(name="status")
    private String status;

    public UserEntity getBuyer() {
        return buyer;
    }

    public OrderEntity setBuyer(UserEntity buyer) {
        this.buyer = buyer;
        return this;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public OrderEntity setItems(List<ItemEntity> items) {
        this.items = items;
        return this;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public OrderEntity setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
        return this;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public OrderEntity setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderEntity setStatus(String status) {
        this.status = status;
        return this;
    }
}
