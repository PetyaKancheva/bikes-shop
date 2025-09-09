package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.dto.OrderDTO;
import bg.softuni.bikes_shop.model.entity.ItemEntity;
import bg.softuni.bikes_shop.model.entity.OrderEntity;
import bg.softuni.bikes_shop.model.entity.UserEntity;
import bg.softuni.bikes_shop.service.impl.ItemServiceImpl;
import bg.softuni.bikes_shop.util.TestDataUtil;
import bg.softuni.bikes_shop.util.TestUserUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.aspectj.runtime.internal.Conversions.doubleValue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrdersControllerTestIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private TestUserUtil testUserUtil;
    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    void setUp() {
        testUserUtil.cleanUp();
        testDataUtil.cleanUp();
    }

    @AfterEach
    void tearDown() {
        testUserUtil.cleanUp();
        testDataUtil.cleanUp();
    }

    @Test
    void testGetOrders() throws Exception {
        CustomUserDetails testAdmin = testUserUtil.createTestAdmin("admin@mail.com");
        OrderEntity orderEntity = testDataUtil.createOrderEntity(testAdmin.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .with(user(testAdmin))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn().getModelAndView().getModel("addOrders");

    }
}