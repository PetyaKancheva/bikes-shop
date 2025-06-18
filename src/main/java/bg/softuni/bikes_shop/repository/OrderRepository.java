package bg.softuni.bikes_shop.repository;

import bg.softuni.bikes_shop.model.dto.OrderDTO;
import bg.softuni.bikes_shop.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  OrderRepository extends JpaRepository<OrderEntity,Long> {
    List<OrderEntity> findAllByBuyerEmail(String email);
}
