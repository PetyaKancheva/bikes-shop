package bg.softuni.bikes_shop.model.events;

import bg.softuni.bikes_shop.model.entity.ProductEntity;
import org.springframework.context.ApplicationEvent;

public class ProductAdditionEvent extends ApplicationEvent {
    private final ProductEntity product;

    public ProductAdditionEvent(Object source, ProductEntity product) {
        super(source);
        this.product = product;
    }

    public ProductEntity getProduct() {
        return product;
    }
}


