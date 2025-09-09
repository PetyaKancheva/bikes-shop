package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserProfileControllerTestIT {

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
    void testProfileGetSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .with(user(testUserUtil.createTestUser("user@mail.com")))
                .with(csrf()))
                .andExpectAll(status().isOk(),view().name("user-profile"));

    }

    @Test
    void testUpdateMainProfileSuccess() throws Exception {
        CustomUserDetails testUser = testUserUtil.createTestUser("user@mail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/main")
                        .param("firstName","new first name")
                        .param("lastName","new last name")
                        .param("address","new address")
                        .param("currentEmail",testUser.getEmail())
                        .with(user(testUser))
                        .with(csrf()))
                .andExpectAll(status().is3xxRedirection())
                .andExpect(flash().attribute("message","Your profile is successfully updated!"));


        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

        Assertions.assertEquals(1, receivedMessages.length);
        MimeMessage updateMessage = receivedMessages[0];

        Assertions.assertTrue(updateMessage.getContent().toString().contains("update"));
        Assertions.assertEquals(1, updateMessage.getAllRecipients().length);
        Assertions.assertEquals(testUser.getEmail(), updateMessage.getAllRecipients()[0].toString());

    }
}