package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.model.dto.AdminUpdateDTO;
import bg.softuni.bikes_shop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;
    private final static String SUCCESSFULLY_UPDATED_OWN_PROFILE_MSG =
            "Your profile is successfully updated!";
    private final static String SUCCESSFULLY_UPDATED_USER_MSG =
            "User %s is now updated!";
    private final static String ATTRIBUTE_MSG_NAME = "message";


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRoles")
    public UserRoleEnum[] roleEnums() {
        return UserRoleEnum.values();
    }

    @GetMapping("/admin")
    private String view(Model model) {

        if (!model.containsAttribute("adminUpdateDTO")) {
            model.addAttribute("adminUpdateDTO", AdminUpdateDTO.empty());
        }

        return "admin-profile";
    }

    @PostMapping("/admin")
    private String search(String personToSearch, Model model) {

        if (!model.containsAttribute("listPeople")) {
            model.addAttribute("listPeople", userService.getAllByEmailFirsOrLastName(personToSearch));
        }

        if (!model.containsAttribute("adminUpdateDTO")) {
            model.addAttribute("adminUpdateDTO", AdminUpdateDTO.empty());
        }


        return "admin-profile";
    }

    @GetMapping("/admin/update/{id}")
    private String selectUser(@PathVariable("id") String email, Model model) {

        if (email.isEmpty()) {
            return "admin-profile";
        }

        if (!model.containsAttribute("adminUpdateDTO")) {
            model.addAttribute("adminUpdateDTO",
                    userService.getAdminDTO(email));
        }

        return "admin-profile";

    }

    @PostMapping("/admin/update/{id}")
    private String updateProfile(Principal principal, @PathVariable("id") String oldEmail,
                                 @Valid AdminUpdateDTO adminUpdateDTO
            , BindingResult bindingResult, RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("adminUpdateDTO", adminUpdateDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.adminUpdateDTO", bindingResult);
            return "redirect:/admin/update/{id}";
        }

        userService.updateByAdmin(adminUpdateDTO, oldEmail);


        if (principal.getName().equals(oldEmail)) {
            rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME, SUCCESSFULLY_UPDATED_OWN_PROFILE_MSG);
            return "redirect:/login?logout";
        } else {
            rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME, String.format(SUCCESSFULLY_UPDATED_USER_MSG, adminUpdateDTO.userMainUpdateDTO().firstName()));
            return "redirect:/admin";
        }

    }
}
