package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.model.entity.OrderEntity;
import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.repository.ItemRepository;
import bg.softuni.bikes_shop.repository.ProductRepository;
import bg.softuni.bikes_shop.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    private ItemServiceImpl testItemService;
    @Mock
    private ItemRepository mockItemRepository;
    @Mock
    private ProductRepository mockProductRepository;
    @BeforeEach
    void setUp() {
        testItemService=new ItemServiceImpl(mockItemRepository,mockProductRepository) ;
    }

    @Test
    void testCreateItem() {
//        arrange
        String testCompositeName="testCompositeName";
        ItemDTO testItemDTO=new ItemDTO().setPrice(1d).setQuantity(1).setProductName("testName").setProductCompositeName(testCompositeName);
        OrderEntity testOrder= new OrderEntity();
        ProductEntity testProductEntity= new ProductEntity().setCompositeName(testCompositeName);

       when(mockProductRepository.findByCompositeName(testItemDTO.getProductCompositeName()))
                .thenReturn( Optional.of(testProductEntity));
//         act
        testItemService.createItem(testItemDTO,testOrder);
//         assert
        Assertions.assertNotNull(mockItemRepository);
    }
    @Test
    void testCreateItemFail(){
        Assertions.assertThrows(CustomObjectNotFoundException.class,() ->testItemService.createItem(new ItemDTO(),new OrderEntity())   );

    }
}