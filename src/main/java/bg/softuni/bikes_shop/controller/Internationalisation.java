package bg.softuni.bikes_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Internationalisation {
    @GetMapping("/internationalisation")
   public String getPage(){


        return "internationalisation";
    }
}
