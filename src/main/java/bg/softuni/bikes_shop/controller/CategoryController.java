package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.service.CurrencyService;
import bg.softuni.bikes_shop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CategoryController {
    private final ProductService productService;
    private final CurrencyService currencyService;
    private final static List<String> CURRENCY_LIST = List.of("EUR", "BGN", "PLN", "USD");

    public CategoryController(ProductService productService, CurrencyService currencyService) {
        this.productService = productService;
        this.currencyService = currencyService;
    }
    @ModelAttribute("listCurrencies")
    public List<String> currencyList() {
        return CURRENCY_LIST;
    }

    @GetMapping("/{category}")
    private String category(@PathVariable("category") String category, Model model, @PageableDefault(size = 9, sort = "name") Pageable pageable,
                            @CookieValue(value = "currency", required = false)String cookie,
                            HttpServletResponse response, HttpServletRequest request) {
        List<String> categories = productService.getDistinctCategories();

        if (!categories.contains(category)) {
            throw new CustomObjectNotFoundException("Category with id: " + category + "not found!");
        }
        model.addAttribute("products", productService.getProductsFromCategoryPageable(pageable, category));
        model.addAttribute("categories", categories);
        model.addAttribute("currentCategory", category);

        if (!model.containsAttribute("currDTO")) {
            model.addAttribute("currDTO", currencyService.getCurrencyDTO(request,response,cookie));
        }
        return "category";
    }


}




