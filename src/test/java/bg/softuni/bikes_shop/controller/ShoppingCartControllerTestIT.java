package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.util.CurrentOrder;
import bg.softuni.bikes_shop.util.TestDataUtil;
import bg.softuni.bikes_shop.util.TestUserUtil;
import jakarta.servlet.http.HttpSession;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.hibernate.validator.constraints.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingCartControllerTestIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TestUserUtil testUserUtil;
    @Autowired
    TestDataUtil testDataUtil;
//    @Autowired
//    HttpSession session;
    @Autowired
    CurrentOrder currentOrder;

//    CustomUserDetails testUser;

    @BeforeEach
    void setUp() {
        testDataUtil.cleanUp();
        testUserUtil.cleanUp();

    }

    @AfterEach
    void tearDown() {
        testDataUtil.cleanUp();
        testUserUtil.cleanUp();
    }

    @Test
    @WithAnonymousUser
    void testGetShoppingCartWithAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/shopping-cart")
                        .with(csrf()))
                .andExpectAll(status().isOk()
                        , model().attribute("currentOrder",  currentOrder)
                        , model().attributeExists("currDTO")
                        , view().name("shopping-cart"));

    }

    @Test
    void testDeleteProduct() throws Exception {
        CustomUserDetails testUser = testUserUtil.createTestUser("user@mail.com");

        ItemDTO itemDTO = new ItemDTO()
                .setPrice(40d)
                .setQuantity(3)
                .setProductName("test product")
                .setProductCompositeName("test_product");

        ItemDTO itemDTO1 = new ItemDTO()
                .setPrice(60d)
                .setQuantity(1)
                .setProductName("test new product")
                .setProductCompositeName("test_new_product");

        currentOrder.add(itemDTO);
        currentOrder.add(itemDTO1);
//        Object attibute = session.getServletContext().getAttribute("currentOrder");




        Assertions.assertEquals(2, currentOrder.getItems().size()); // works
//

//
//    CurrentOrder currentOrder = new CurrentOrder();
//
//
//        session.setAttribute("currentOrder", currentOrder);
//        session.getServletContext().getAttributeNames().
//
//    ItemDTO itemDTO = new ItemDTO()
//            .setPrice(40d)
//            .setQuantity(3)
//            .setProductName("test product")
//            .setProductCompositeName("test_product");
//        currentOrder.add(itemDTO);
//
    String productID =currentOrder.getItems().getFirst().getProductCompositeName(); // works
//ApplicationContext
//first login??
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .with(user(testUser))
                .with(csrf()))
                        .andExpect(status().isOk());

                mockMvc.perform(MockMvcRequestBuilders.post("/shopping-cart/delete")
                .param("productID", productID)
                        .with(user(testUser))
            .with(csrf()))
            .andExpectAll(status().is3xxRedirection());
    }


}