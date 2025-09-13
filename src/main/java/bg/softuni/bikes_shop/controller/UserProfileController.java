package bg.softuni.bikes_shop.controller;


import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.dto.UserUpdateEmailDTO;
import bg.softuni.bikes_shop.model.dto.UserUpdateMainDetailsDTO;
import bg.softuni.bikes_shop.model.dto.UserUpdatePasswordDTO;
import bg.softuni.bikes_shop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserProfileController {
    private final UserService userService;
    private final static String SUCCESSFULLY_UPDATED_OWN_PROFILE_MSG =
            "Your profile is successfully updated!";
    private final static String ATTRIBUTE_MSG_NAME = "message";

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    private String profileGet(Principal principal, Model model) {
        String email=principal.getName();

        if (!model.containsAttribute("userUpdateMainDetailsDTO")) {
            model.addAttribute("userUpdateMainDetailsDTO", userService.getUserMainUpdateDTO(email));
        }
        if (!model.containsAttribute("userUpdatePasswordDTO")) {
            model.addAttribute("userUpdatePasswordDTO", UserUpdatePasswordDTO.empty());
        }
        if (!model.containsAttribute("userUpdateEmailDTO")) {
              model.addAttribute("userUpdateEmailDTO", new UserUpdateEmailDTO(email,null,null,null));
        }


        return "user-profile";
    }

    @PostMapping("/user/main")
    private String mainProfileUpdate(Principal principal, @Valid UserUpdateMainDetailsDTO userUpdateMainDetailsDTO,
                                     BindingResult bindingResult, RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("userUpdateMainDetailsDTO", userUpdateMainDetailsDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateMainDetailsDTO", bindingResult);
            return "redirect:/user";
        }

        userService.updateMainUserDetails(userUpdateMainDetailsDTO);

        rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME, SUCCESSFULLY_UPDATED_OWN_PROFILE_MSG);

        return "redirect:/login?logout";

    }

    @PostMapping("/user/password")
    private String passwordUpdate(@Valid UserUpdatePasswordDTO userUpdatePasswordDTO, BindingResult bindingResult, RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("userUpdatePasswordDTO", userUpdatePasswordDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userUpdatePasswordDTO", bindingResult);
            return "redirect:/user";
        }

        userService.updatePassword(userUpdatePasswordDTO);
        // add different for pass and email?
        rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME, SUCCESSFULLY_UPDATED_OWN_PROFILE_MSG);

        return "redirect:/login?logout";
//        return "redirect:/user";
    }

    @PostMapping("/user/email")
    private String emailUpdate( @Valid UserUpdateEmailDTO userUpdateEmailDTO, BindingResult bindingResult, RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("userUpdateEmailDTO", userUpdateEmailDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateEmailDTO", bindingResult);
            return "redirect:/user";
        }

        userService.updateEmail(userUpdateEmailDTO);

        rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME, SUCCESSFULLY_UPDATED_OWN_PROFILE_MSG);

        return "redirect:/login?logout";
//        return "redirect:/user";
    }


}
