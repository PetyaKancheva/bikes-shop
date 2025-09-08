package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.dto.ProductDTO;
import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.repository.ProductRepository;
import bg.softuni.bikes_shop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    private ProductServiceImpl serviceToTest;
    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private ApplicationEventPublisher testAppPub;

    @BeforeEach
    void setUp() {
        serviceToTest = new ProductServiceImpl(mockProductRepository, testAppPub);
    }

    @Test
    void getSingleProductCorrectly() {

        ProductEntity testEntity = createEntity();

        when(mockProductRepository.findByCompositeName(testEntity.getCompositeName()))
                .thenReturn(Optional.of(testEntity));

        ProductDTO foundEntity = serviceToTest.getSingleProduct(testEntity.getCompositeName()).orElseThrow();

        Assertions.assertNotNull(foundEntity);
        Assertions.assertEquals(testEntity.getCompositeName(), foundEntity.compositeName());
        Assertions.assertEquals(testEntity.getPrice(), BigDecimal.valueOf(foundEntity.price()));

    }

//        category, name, description
    // TODO make in one go

    @Test
    void testSearchForProductsWithName() {
        ProductEntity testEntity = createEntity();
        // crate page of Entity
        String name= testEntity.getName();
        Page<ProductEntity> testPages = new PageImpl<ProductEntity>(List.of(testEntity), Pageable.unpaged(), 1);

        when(mockProductRepository.searchAllByDescriptionContainingOrCategoryOrNameContaining(name,name,name, Pageable.unpaged()))
                .thenReturn(testPages);

        Page<ProductDTO> result = serviceToTest.searchForProducts(testEntity.getName());

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(name, result.getContent().getFirst().name());


    }

    @Test
    void testSearchForProductsWithCategory() {
        ProductEntity testEntity = createEntity();
        // crate page of Entity
        String category= testEntity.getCategory();
        Page<ProductEntity> testPages = new PageImpl<ProductEntity>(List.of(testEntity), Pageable.unpaged(), 1);

        when(mockProductRepository.searchAllByDescriptionContainingOrCategoryOrNameContaining(category,category,category, Pageable.unpaged()))
                .thenReturn(testPages);

        Page<ProductDTO> result = serviceToTest.searchForProducts(testEntity.getCategory());

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(category, result.getContent().getFirst().category());

    }

    @Test
    void testSearchForProductsWithDescription() {
        ProductEntity testEntity = createEntity();

        String description= testEntity.getDescription();
        Page<ProductEntity> testPages = new PageImpl<ProductEntity>(List.of(testEntity), Pageable.unpaged(), 1);

        when(mockProductRepository.searchAllByDescriptionContainingOrCategoryOrNameContaining(description,description,description, Pageable.unpaged()))
                .thenReturn(testPages);

        Page<ProductDTO> result = serviceToTest.searchForProducts(testEntity.getDescription());

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(description, result.getContent().getFirst().description());
    }

    @Test
    void testGetProducts() {
        Integer size = 1;
        Integer page = 0;
        String sort = "name: asc";
        
        ProductEntity testEntity = createEntity();
        Pageable pageable= createPageable(size, page, sort);

        // crate page of Entity
        List<ProductEntity> listOfProducts = List.of( testEntity,testEntity,testEntity);

        Page<ProductEntity> testPages = new PageImpl<>(listOfProducts, pageable, listOfProducts.size());

        when(mockProductRepository.findAllProductsWithCompositeNameNotNull(pageable)).thenReturn(testPages);

        Page<ProductDTO> result = serviceToTest.getProducts(size, page, sort);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(listOfProducts.size(),result.getTotalPages());
        Assertions.assertEquals(testEntity.getName(),result.getContent().get(0).name());

    }
    private static Pageable createPageable(Integer size,Integer page, String sort) {

        Sort.Order order = new Sort.Order(Sort.Direction.fromString("asc"), "name");
        return  PageRequest.of(page, size, Sort.by(order));
    }

  


    @Test
    void testGetProductsFromCategoryPageable() {

        ProductEntity testEntity = createEntity();
        Pageable pageable =createPageable(1,0,"name: asc");

        List<ProductEntity> listOfProducts = List.of( testEntity,testEntity,testEntity);

        Page<ProductEntity> testPages = new PageImpl<>(listOfProducts, pageable, listOfProducts.size());


        when(mockProductRepository.findByCategory(pageable,testEntity.getCategory())).thenReturn(testPages);

        Page<ProductDTO> result=serviceToTest.getProductsFromCategoryPageable(pageable,testEntity.getCategory());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(listOfProducts.size(),result.getTotalPages());
        Assertions.assertEquals(testEntity.getName(),result.getContent().get(0).name());

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