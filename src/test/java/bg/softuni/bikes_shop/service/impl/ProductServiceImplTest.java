package bg.softuni.bikes_shop.service.impl;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.dto.ProductDTO;
import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    private ProductServiceImpl serviceToTest;
    @Mock
    private ProductRepository mockProductRepository;

    private ApplicationEventPublisher testAppPub;

    @BeforeEach
    void setUp() {
        serviceToTest = new ProductServiceImpl(mockProductRepository, testAppPub);
    }

    @Test
    void getSingleProductCorrectly() {
        //arrange

        ProductEntity testEntity = createEntity( );

        when(mockProductRepository.findByCompositeName(testEntity.getCompositeName()))
                .thenReturn(Optional.of(testEntity));

        // act
        ProductDTO foundEntity = serviceToTest.getSingleProduct(testEntity.getCompositeName()).orElseThrow();

        // assert
        Assertions.assertNotNull(foundEntity);
        Assertions.assertEquals(testEntity.getCompositeName(), foundEntity.compositeName());
        Assertions.assertEquals(testEntity.getPrice(), BigDecimal.valueOf(foundEntity.price()));

    }
    @Test
    void testSearchForProducts(){
        ProductEntity testEntity= createEntity();

//        category, name, description

  // crate page of Entity
        Page<ProductEntity> testPages= new PageImpl<ProductEntity>(List.of(testEntity),Pageable.unpaged(),1);

       when( mockProductRepository.findAllByKeyword(testEntity.getName(), Pageable.unpaged()))
               .thenReturn(testPages);

       Page<ProductDTO> foundResult=serviceToTest.searchForProducts(testEntity.getName());
       Assertions.assertEquals(foundResult.getTotalElements(),1);


    }

    private static ProductEntity createEntity() {
        return new ProductEntity()
                .setName("test name")
                .setCompositeName("composite_name")
                .setCategory("CATEGORY")
                .setPrice(BigDecimal.valueOf(1000d))
                .setDescription("test description");
    }

}