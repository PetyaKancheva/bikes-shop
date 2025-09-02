package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.service.UserRoleService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BeanPropertyBindingResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

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
     void setUp(){
        greenMail = new GreenMail(new ServerSetup(port,host,"smtp"));
        greenMail.start();
        greenMail.setUser(username,password);
    }
    @AfterEach
    void tearDown(){
        greenMail.stop();
    }

    @Test
    void testRegistrationSuccess() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .param("email","ivan@mail.com")
                .param("firstName","Ivan")
                .param("lastName","Ivanov")
                .param("address", "Sofia")
                .param("country","Bulgaria")
                .param("password","test1234")
                .param("confirmPassword","test1234")
                .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message",
                        "Congratulations! You are now registered at Bikes-Shop. Please proceed to authenticate your email address."))
                .andExpect(view().name("redirect:/register"));


        greenMail.waitForIncomingEmail(1);
      th:

        Assertions.assertEquals(1, receivedMessages.length);
        MimeMessage registrationMessage = receivedMessages[0];

        Assertions.assertTrue(registrationMessage.getContent().toString().contains("Ivan"));
        Assertions.assertEquals(1, registrationMessage.getAllRecipients().length);
        Assertions.assertEquals("ivan@mail.com", registrationMessage.getAllRecipients()[0].toString());

    }
    @Test
    void testGet() throws Exception {
                 mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                    .andExpect(status().isOk());
    }
    @Test
    void testFailRegistrationPassNotMatch() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("email","ivan@mail.com")
                        .param("firstName","Ivan")
                        .param("lastName","Ivanov")
                        .param("address", "Sofia")
                        .param("country","Bulgaria")
                        .param("password","test1234")
                        .param("confirmPassword","" )
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(model().attributeHasErrors());


    }




}