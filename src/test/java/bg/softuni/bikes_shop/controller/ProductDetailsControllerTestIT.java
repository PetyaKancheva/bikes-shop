package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.util.TestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ProductDetailsControllerTestIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TestDataUtil testDataUtil;
    @BeforeEach
    void setUp() {
        testDataUtil.cleanUp();
    }

    @AfterEach
    void tearDown() {
        testDataUtil.cleanUp();
    }

    @Test
    void testGetDetailsSuccess() throws Exception {
        ProductEntity testProduct=testDataUtil.createTestProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{composite_name}",testProduct.getCompositeName())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("product-details"));
    }
}