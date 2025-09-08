package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.repository.ProductRepository;
import bg.softuni.bikes_shop.util.TestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.*;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    void setUp() {
        testDataUtil.cleanUp();
    }

    @AfterEach
    void tearDown() {
        testDataUtil.cleanUp();
    }

    @Test
    void testGetIndexSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .with(csrf()))
                .andExpectAll(status().isOk(), view().name("index")
                        , model().attributeExists("products")
                        , model().attributeExists("currDTO")
                        , model().attributeExists("listCurrencies")
                        , model().attributeExists("categories"));

    }

    @Test
    void testGetAboutSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about"))
                .andExpectAll(status().isOk(), view().name("/static/about"));
    }

    @Test
    void testGetServicesSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/services"))
                .andExpectAll(status().isOk(), view().name("/static/services"));
    }

    @Test
    void testGetContactsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts"))
                .andExpectAll(status().isOk(), view().name("/static/contacts"));
    }

    @Test
    void testGetError500Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/error/500"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testGetSearchResultSuccess() throws Exception {
        ProductEntity testProduct = testDataUtil.createTestProduct();

        mockMvc.perform(MockMvcRequestBuilders.post("/search-result")
                        .param("productToSearch", testProduct.getName())
                        .with(csrf()))
                .andExpect(status().isOk());


    }

    @Test
    void testGetSearchResultFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/search-result")
                        .param("productToSearch", "something")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpectAll(flash().attributeExists("message"));
    }

    @Test
    void testChangeCurrencyBGN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currency")
                        .param("selectedCurrency", "BGN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().value("currency","BGN"));

    }
}