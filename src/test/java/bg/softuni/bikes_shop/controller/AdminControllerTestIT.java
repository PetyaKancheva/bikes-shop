package bg.softuni.bikes_shop.controller;

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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTestIT {

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
    void testAdminGetProfileSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin")
                        .with(user(testUserUtil.createTestAdmin("test@mail.com")))
                        .param("personToSearch", "testName")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admin-profile"));
    }
    @Test
    void testAdminGetProfileAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin")
                        .with(user(testUserUtil.createTestEmployee("test@mail.com")))
                        .param("personToSearch", "testName")
                        .with(csrf())
                )
                .andExpect(status().isForbidden());

    }
    @Test
    void testAdminUpdateProfileSuccess() throws Exception {
        String testEmail="b@mail.com";
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/update/{id}",testEmail)
                        .with(user(testUserUtil.createTestAdmin("admin@mail.com")))
                        .param("email", testEmail)
//                . ("adminUpdateDTO",testDataUtil.createTestAdminUpdateDTO())
                        .with(csrf())
                ).andDo(print());
//                .andExpect(model().size(1));
//                .andExpect(view().name("admin-profile"));
    }
}
