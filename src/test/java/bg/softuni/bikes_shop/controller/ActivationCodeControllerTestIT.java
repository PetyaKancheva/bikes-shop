package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.entity.UserActivationCodeEntity;
import bg.softuni.bikes_shop.repository.ActivationCodeRepository;
import bg.softuni.bikes_shop.util.TestDataUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ActivationCodeControllerTestIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TestDataUtil testDataUtil;


    @BeforeEach
    void setUp(){
        testDataUtil.cleanUp();
    }
    @AfterEach
    void tearDown(){
        testDataUtil.cleanUp();
    }


    @Test
    void  testActivateUserFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/activate/")
                        .param("activation_code","asdasd")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message","Not able to activate code."));
    }
    @Test
    void  testActivateUserSuccess() throws Exception {

        UserActivationCodeEntity testEntity=testDataUtil.createActivationCode();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/activate/")
                        .param("activation_code",testEntity.getActivationCode())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message","Congratulations! Account is now activated! Please log in. "));
    }


}