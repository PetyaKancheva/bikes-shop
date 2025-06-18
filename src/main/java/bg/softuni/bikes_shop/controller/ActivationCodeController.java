package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.service.UserActivationService;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ActivationCodeController {
    private final UserActivationService userActivationService;
    private final static String ACTIVATION_FAIL_MSG ="Not able to activate code.";
    private final static String ACTIVATION_SUCCESS_MSG ="Congratulations! Account is now activated! Please log in. ";
    private final static String ATTRIBUTE_MSG_NAME = "message";

    public ActivationCodeController(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }


    @GetMapping ("/user/activate/")
    private String activateUser(@RequestParam @Size(min = 15,max=15) String activation_code, RedirectAttributes rAtt){

        if(userActivationService.userActivate(activation_code)){
            rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME, ACTIVATION_SUCCESS_MSG);
        }else{
            rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME,ACTIVATION_FAIL_MSG);
        };


        return "redirect:/";
    }
}
