package bg.softuni.bikes_shop.service.impl;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.model.entity.ItemEntity;
import bg.softuni.bikes_shop.model.entity.OrderEntity;
import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.repository.ItemRepository;
import bg.softuni.bikes_shop.repository.ProductRepository;
import bg.softuni.bikes_shop.service.ItemService;
import org.springframework.stereotype.Service;

import static org.aspectj.runtime.internal.Conversions.doubleValue;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ItemServiceImpl(ItemRepository itemRepository, ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createItem(ItemDTO itemDTO, OrderEntity orderEntity) {
        ProductEntity existingProduct=productRepository.findByCompositeName(itemDTO.getProductCompositeName())
                .orElseThrow(() -> new CustomObjectNotFoundException("Product with id: " + itemDTO.getProductName() + " not found!"));

            ItemEntity newItem=new ItemEntity()
                    .setProduct(existingProduct)
                    .setQuantity(itemDTO.getQuantity())
                    .setOrder(orderEntity);

        itemRepository.save(newItem);

    }

    public static ItemDTO maptItemDTO(ItemEntity itemsEntity) {
        return new ItemDTO()
                .setProductCompositeName(itemsEntity.getProduct().getCompositeName())
                .setProductName(itemsEntity.getProduct().getName())
                .setQuantity(itemsEntity.getQuantity())
                .setPrice(doubleValue(itemsEntity.getProduct().getPrice()));
    }
}
