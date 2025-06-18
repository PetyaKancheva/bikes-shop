package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.model.dto.OrderDTO;
import bg.softuni.bikes_shop.util.CurrentOrder;

import java.util.List;

public interface OrderService {
   void buy(String email, CurrentOrder currentOrder);

    List<OrderDTO> getAllByUser(String email);
}
