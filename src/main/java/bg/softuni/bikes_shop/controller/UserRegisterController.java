package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.dto.UserRegisterDTO;
import bg.softuni.bikes_shop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserRegisterController {
    private final UserService userService;
    private final static String SUCCESSFUL_REGISTRATION_MSG =
            "Congratulations! You are now registered at Bikes-Shop. Please proceed to authenticate your email address.";
    private final static String ATTRIBUTE_MSG_NAME="message";

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    private String register(Model model){

        if (!model.containsAttribute("userRegisterDTO")) {
           model.addAttribute("userRegisterDTO", UserRegisterDTO.empty());
        }

        return "user-register";

    }
    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult, RedirectAttributes rAtt) {

        if(bindingResult.hasErrors()){
            rAtt.addFlashAttribute("userRegisterDTO",userRegisterDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:/register";
        }

        userService.register(userRegisterDTO);
        rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME, SUCCESSFUL_REGISTRATION_MSG);
        return "redirect:/register";

    }


}
