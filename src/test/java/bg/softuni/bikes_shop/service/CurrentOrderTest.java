package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.util.CurrentOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrentOrderTest {
    @Mock
    CurrentOrder currentOrder;

    @Test
    void testAddItem() {

    ItemDTO itemDTO = new ItemDTO()
            .setPrice(40d)
            .setQuantity(3)
            .setProductName("test product")
            .setProductCompositeName("test_product");

       when(currentOrder.getItems()).thenReturn(List.of(itemDTO));

        Assertions.assertEquals(1, currentOrder.getItems().size());
    }

    @Test
    void testDeleteItem() {

        ItemDTO itemDTO = new ItemDTO()
                .setPrice(40d)
                .setQuantity(3)
                .setProductName("test product")
                .setProductCompositeName("test_product");
        CurrentOrder testCO= new CurrentOrder();
        testCO.setItems(List.of(itemDTO));

        currentOrder.deleteItem(itemDTO.getProductCompositeName());
     verify(currentOrder,times(1)).deleteItem(itemDTO.getProductCompositeName());
    }
}