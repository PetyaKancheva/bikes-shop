package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.util.CurrentOrder;
import bg.softuni.bikes_shop.util.TestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        ProductEntity testProduct = testDataUtil.createTestProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{composite_name}", testProduct.getCompositeName())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("product-details"));
    }

    @Test
    void testGetDetailsFail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/product/{composite_name}", "emptyName")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testBuySuccess() throws Exception {
        ProductEntity testProduct = testDataUtil.createTestProduct();

        mockMvc.perform(MockMvcRequestBuilders.post("/product/{composite_name}", testProduct.getCompositeName())
                        .param("productCompositeName", testProduct.getCompositeName())
                        .param("productName", testProduct.getName())
                        .param("price", "1000")
                        .param("quantity", "2")
                        .with(csrf()))
                .andExpect( status().is3xxRedirection())
                .andExpect(flash().attribute("message",containsString("Successfully purchased:")));
    }
    @Test
    @WithMockUser(roles = {"USER"})
    void testBuyError() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/product/{composite_name}", "some name")
                        .param("productCompositeName", "some name")
                        .with(csrf()))
                .andExpect( model().attributeHasErrors());

    }
}