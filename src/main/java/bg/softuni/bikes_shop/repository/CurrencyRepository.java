package bg.softuni.bikes_shop.repository;

import bg.softuni.bikes_shop.model.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity,Long> {
    CurrencyEntity findByName(String name);
}
