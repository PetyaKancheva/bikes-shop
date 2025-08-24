package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.repository.ActivationCodeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ActivationCodeControllerTestIT {
    @Autowired
    MockMvc mockMvc;


    @Test
    void  testActivateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/activate/")
                        .param("activation_code","jmCY4WoeBarWrdb")
                .with(csrf()))
                .andExpect(status().isOk());

    }


}