package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.service.CurrencyService;
import bg.softuni.bikes_shop.service.ProductService;
import bg.softuni.bikes_shop.util.CurrentOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductDetailsController {
    private final ProductService productService;
    private final static String SUCCESSFUL_PURCHASE_MSG =  "Successfully purchased: %s. Please go to shopping cart to see your current items.";
    private final static String ATTRIBUTE_MSG_NAME = "message";
    private final CurrencyService currencyService;
    private final CurrentOrder currentOrder;
    private final static List<String> CURRENCY_LIST = List.of("EUR", "BGN", "PLN", "USD");
    public ProductDetailsController(ProductService productService, CurrencyService currencyService, CurrentOrder currentOrder) {
        this.productService = productService;
        this.currencyService = currencyService;
        this.currentOrder = currentOrder;
    }
    @ModelAttribute("listCurrencies")
    public List<String> currencyList() {
        return CURRENCY_LIST;
    }

    @GetMapping("/product/{composite_name}")
    public String details(@PathVariable("composite_name") String compositeName, Model model, @CookieValue(value = "currency", required = false)String cookie,
                          HttpServletResponse response, HttpServletRequest request) {

        if (!model.containsAttribute("singleProduct")) {
            model.addAttribute("singleProduct", productService.getSingleProduct(compositeName)
                    .orElseThrow(() -> new CustomObjectNotFoundException("Product with name " + compositeName + " not found!")));
        }

        if (!model.containsAttribute("itemDTO")) {
            model.addAttribute("itemDTO", new ItemDTO());
        }
        if (!model.containsAttribute("currDTO")) {
            model.addAttribute("currDTO", currencyService.getCurrencyDTO(request,response,cookie));
        }

        return "product-details";
    }
    // preauthorise`?
    @PostMapping("/product/{composite_name}")
    public String buy(@Valid ItemDTO itemDTO, BindingResult bindingResult, RedirectAttributes rAtt) {


        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.itemDTO", bindingResult);
          return  "redirect:/product/{composite_name}";
        }


        currentOrder.add(itemDTO);

        rAtt.addFlashAttribute(ATTRIBUTE_MSG_NAME, String.format(SUCCESSFUL_PURCHASE_MSG, itemDTO.getProductName()));

        return "redirect:/product/{composite_name}";
    }


}

