package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.util.TestDataUtil;
import bg.softuni.bikes_shop.util.TestUserUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
