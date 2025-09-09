package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.model.dto.OrderDTO;
import bg.softuni.bikes_shop.model.entity.*;
import bg.softuni.bikes_shop.repository.*;
import bg.softuni.bikes_shop.service.impl.ItemServiceImpl;
import bg.softuni.bikes_shop.service.impl.OrderServiceImpl;
import bg.softuni.bikes_shop.util.CurrentOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.aspectj.runtime.internal.Conversions.doubleValue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderServiceImpl serviceToTest;

    private ItemServiceImpl itemServiceTest;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private OrderServiceImpl mockOrderService;

    @BeforeEach
    void setUp() {
        serviceToTest = new OrderServiceImpl(mockUserRepository, mockOrderRepository, itemServiceTest);
    }

    @Test
    void testBuyProduct() {
        String email = "user@mail.com";
        int itemsCount = 2;
        ProductEntity testProduct = createProductEntity();
        CurrentOrder currentOrder = createCurrentOrder(itemsCount, testProduct);

        mockOrderService.buy(email,currentOrder );
        verify( mockOrderService, times(1)).buy(email, currentOrder);
    }

    @Test
    void testGetAllWithOneOrder() {

        String email = "user@mail.com";
        int itemsCount = 10;
        int ordersCount = 2;

        List<ItemEntity> testItems = createListItemEntity(itemsCount);
        List<OrderEntity> testOrders = createListOrderEntity(ordersCount, testItems);

        when(mockOrderRepository.findAllByBuyerEmail(email).stream().toList())
                .thenReturn(testOrders);

        List<OrderDTO> result = serviceToTest.getAllByUser(email);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(doubleValue(testOrders.getFirst().getTotalSum()), result.getFirst().totalSum());
        Assertions.assertEquals(testItems.size(), result.getFirst().items().size());
        Assertions.assertEquals(testOrders.size(), result.size());

    }

    private ProductEntity createProductEntity() {
        return new ProductEntity()
                .setName("test product")
                .setCompositeName("test_product")
                .setDescription("test description")
                .setPrice(BigDecimal.valueOf(1000))
                .setCategory("testCategory")
                .setPictureURL("urlTest");
    }

    private List<OrderEntity> createListOrderEntity(int orderCount, List<ItemEntity> items) {

        List<OrderEntity> mockOrders = new ArrayList<>();
        for (int i = 0; i < orderCount; i++) {
            mockOrders.add(createTestOrderEntity(items));
        }
        return mockOrders;
    }

    private OrderEntity createTestOrderEntity(List<ItemEntity> items) {

        return new OrderEntity()
                .setBuyer(new UserEntity())
                .setDateCreated(LocalDate.now())
                .setStatus("open")
                .setTotalSum(BigDecimal.valueOf(1000))
                .setItems(items);

    }

    private List<ItemEntity> createListItemEntity(int itemsCount) {

        List<ItemEntity> mockItems = new ArrayList<>();
        for (int i = 0; i < itemsCount; i++) {
            mockItems.add(createItemEntity());
        }
        return mockItems;
    }

    private ItemEntity createItemEntity() {

        ItemEntity itemEntity = new ItemEntity().setProduct(createProductEntity()
        ).setQuantity(2);
//            itemEntity.setOrder(createTestOrderEntity(createListItemEntity(2)));
        return itemEntity;

    }

    private CurrentOrder createCurrentOrder(int itemsCount, ProductEntity product) {
        CurrentOrder currentOrder = new CurrentOrder();

        for (int i = 0; i < itemsCount; i++) {
            currentOrder.add(
                    new ItemDTO()
                            .setPrice(doubleValue(product.getPrice()))
                            .setProductName(product.getName())
                            .setProductCompositeName(product.getCompositeName())
                            .setQuantity(2));
        }
        return currentOrder;

    }
}