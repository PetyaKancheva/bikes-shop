package bg.softuni.bikes_shop.controller;


import bg.softuni.bikes_shop.model.dto.UserUpdateMainDetailsDTO;
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

        if (!model.containsAttribute("userUpdateDTO")) {
//            model.addAttribute("userUpdateDTO",
//                    new UserUpdaterMainDetailsDTO(
//                            userService.getUserMainUpdateDTO(principal.getName()),
//                            new UserUpdatePasswordDTO(principal.getName(), "", "")));
        }

        return "user-profile";
    }

    @PostMapping("/user")
    private String mainProfileUpdate(Principal principal, @Valid UserUpdateMainDetailsDTO userUpdateMainDetailsDTO,
                                 BindingResult bindingResult, RedirectAttributes rAtt) {
        System.out.println();

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("updateUserMainDetailsDTO", userUpdateMainDetailsDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.updateUserMainDetailsDTO", bindingResult);
            return "redirect:/user";
        }

        userService.updateMainUserDetails(userUpdateMainDetailsDTO);

        rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME, SUCCESSFULLY_UPDATED_OWN_PROFILE_MSG);

        return "redirect:/login?logout";

    }


}
