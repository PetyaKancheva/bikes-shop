package bg.softuni.bikes_shop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name="currency")
public class CurrencyEntity {
    @Id
    @Column(name="name",nullable = false)
    String name;
    @Column(name="rate",nullable = false)
    BigDecimal rate;

    public String getName() {
        return name;
    }

    public CurrencyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public CurrencyEntity setRate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }
}
