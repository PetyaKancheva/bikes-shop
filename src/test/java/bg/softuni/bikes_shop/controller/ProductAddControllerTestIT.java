package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.dto.ProductAddDTO;
import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.model.entity.UserActivationCodeEntity;
import bg.softuni.bikes_shop.model.entity.UserEntity;
import bg.softuni.bikes_shop.util.TestDataUtil;
import bg.softuni.bikes_shop.util.TestUserUtil;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductAddControllerTestIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUserUtil testUserUtil;
    @Autowired
    private TestDataUtil testDataUtil;


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
    void testGetPageAddProduct() throws Exception {
        CustomUserDetails testEmployee = testUserUtil.createTestEmployee("test@mail.com");

        mockMvc.perform(MockMvcRequestBuilders.get("/product/add")
                        .with(user(testEmployee))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasNoErrors())
                .andExpect(view().name("product-add"));
    }

    @Test
    void testGetPageAddProductError() throws Exception {

        CustomUserDetails testEmployee = testUserUtil.createTestEmployee("test@mail.com");

        mockMvc.perform(MockMvcRequestBuilders.get("/product/add")
                        .param("compositeName", "some value")
                        .with(user(testEmployee))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors());
    }

    @Test
    @WithAnonymousUser
    void testAnonymousAddProductFails() throws Exception {
        ProductEntity product = testDataUtil.createTestProduct();
        mockMvc.perform(MockMvcRequestBuilders.post("/product/add")
                        .param("name", "tesName")
                        .param("description", "Test description")
                        .param("price", "1000")
                        .param("category", "testCategory")
                        .param("pictureURL", "urlTest")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    @WithMockUser(roles = {"EMPLOYEE"})
    void testEmployeeAddProductSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/product/add")
                        .param("name", "tesName")
                        .param("description", "Test description")
                        .param("price", "1000")
                        .param("category", "testCategory")
                        .param("pictureURL", "urlTest")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/add"));
    }

    @Test
    @WithMockUser(roles = {"EMPLOYEE"})
    void testAddProductError() throws Exception {
        CustomUserDetails testEmployee = testUserUtil.createTestEmployee("employee@mail.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/product/add")
                        .param("name", "a")
                        .param("price", "2")
                        .param("description", "Test description")
                        .param("category", "category")
                        .param("pictureURL", "urlTest")
                        .with(user(testEmployee))
                        .with(csrf())
                ).andExpect(model().hasErrors())
                .andExpect(status().isOk())
                .andExpect( view().name("product-add"));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testNonEmployeeAddProductFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/product/add")
                        .param("name", "tesName")
                        .param("description", "Test description")
                        .param("price", "1000")
                        .param("category", "testCategory")
                        .param("pictureURL", "urlTest")
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
    }


}
