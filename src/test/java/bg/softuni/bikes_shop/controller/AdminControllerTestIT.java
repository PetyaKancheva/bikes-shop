package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.entity.UserEntity;
import bg.softuni.bikes_shop.util.TestDataUtil;
import bg.softuni.bikes_shop.util.TestUserUtil;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

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

    @Value("${mail.port}")
    private int port;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {

        greenMail = new GreenMail(new ServerSetup(port,host,"smtp"));
        greenMail.start();
        greenMail.setUser(username,password);
        testDataUtil.cleanUp();
        testUserUtil.cleanUp();

    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
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
        CustomUserDetails testAdmin = testUserUtil.createTestAdmin("admin@mail.com");

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/update/{id}", testAdmin.getEmail())
                .with(user(testAdmin))
                .with(csrf())
        ).andExpect(view().name("admin-profile"));
    }

    @Test
    void testGetView() throws Exception {
        CustomUserDetails testAdmin = testUserUtil.createTestAdmin("admin@mail.com");

        mockMvc.perform(MockMvcRequestBuilders.get("/admin")
                        .with(user(testAdmin))
                        .with(csrf())
                ).andExpect(model().attributeExists())
                .andExpect(view().name("admin-profile"));

    }

    @Test
    void testUpdateProfile() throws Exception {
        CustomUserDetails testAdmin = testUserUtil.createTestAdmin("admin@mail.com");

        CustomUserDetails testUser = testUserUtil.createTestUser("user@mail.com");


        mockMvc.perform(MockMvcRequestBuilders.post("/admin/update/{id}",testUser.getEmail())
                .param("oldEmail",testUser.getEmail())
                .param("firstName","new first name")
                .param("lastName","new last name")
                .param("address","new address")
                .param("newEmail","new@mail.com")
                .param("roles" ,"USER")
                .param("newPassword","test125")
                .with(user(testAdmin))
                .with(csrf())

        ).andExpect(status().is3xxRedirection());

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

        Assertions.assertEquals(1, receivedMessages.length);
        MimeMessage registrationMessage = receivedMessages[0];

        Assertions.assertTrue(registrationMessage.getContent().toString().contains("update"));
        Assertions.assertEquals(1, registrationMessage.getAllRecipients().length);
        Assertions.assertEquals("new@mail.com", registrationMessage.getAllRecipients()[0].toString());
    }

}
