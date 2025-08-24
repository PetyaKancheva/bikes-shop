package bg.softuni.bikes_shop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.View;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserLoginControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login-error").with(csrf())).andExpect(status().is3xxRedirection());
    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user-login"));
    }
}