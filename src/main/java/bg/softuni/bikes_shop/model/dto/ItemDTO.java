package bg.softuni.bikes_shop.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// TODO check if it can be made with record
public class ItemDTO{
     @NotNull
    String productCompositeName;
    @NotNull
    String productName;
    @Positive
    Double price;
    @NotNull
    @Positive
    Integer quantity;

    public ItemDTO() {
    }

    public String getProductCompositeName() {
        return productCompositeName;
    }

    public ItemDTO setProductCompositeName(String productCompositeName) {
        this.productCompositeName = productCompositeName;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ItemDTO setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ItemDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ItemDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
