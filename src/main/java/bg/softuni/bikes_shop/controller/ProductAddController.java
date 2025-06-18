package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.dto.ProductAddDTO;
import bg.softuni.bikes_shop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/product")
public class ProductAddController {
    private final ProductService productService;
    private final static String SUCCESSFUL_PRODUCT_ADDITION_MSG =
            "New Product created!";
    private final static String ATTRIBUTE_MSG_NAME = "message";

    public ProductAddController(ProductService productService) {
        this.productService = productService;

    }

    @GetMapping("/add")
    private String addProduct(Model model) {

        if (!model.containsAttribute("productAddDTO")) {
            model.addAttribute("productAddDTO", ProductAddDTO.empty());
        }

        return "product-add";
    }

    @PostMapping("/add")
    private String addProduct(@Valid ProductAddDTO productAddDTO, BindingResult bindingResult, RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("productAddDTO", productAddDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.productAddDTO", bindingResult);
            return "product-add";
        }

        productService.addProduct(productAddDTO);
        rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME,SUCCESSFUL_PRODUCT_ADDITION_MSG);
        return "redirect:/product/add";
    }
}
