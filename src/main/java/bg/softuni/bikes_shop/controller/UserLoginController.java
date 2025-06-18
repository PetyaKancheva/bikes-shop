package bg.softuni.bikes_shop.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserLoginController {

    @GetMapping("/login")
    private String login() {
        return "user-login";
    }

    @PostMapping("/login-error")
    public String loginError(RedirectAttributes rAtt) {
        rAtt.addFlashAttribute("message", "Invalid username or password.");
        return "redirect:/login";
    }

}
