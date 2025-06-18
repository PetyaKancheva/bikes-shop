package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.model.entity.ItemEntity;
import bg.softuni.bikes_shop.model.entity.OrderEntity;

public interface ItemService {
    void createItem (ItemDTO itemDTO, OrderEntity orderEntity);


}
