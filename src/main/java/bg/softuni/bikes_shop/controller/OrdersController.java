package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.dto.OrderDTO;
import bg.softuni.bikes_shop.service.CurrencyService;
import bg.softuni.bikes_shop.service.OrderService;
import bg.softuni.bikes_shop.util.CurrentOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrdersController {

    private final CurrencyService currencyService;
    private final OrderService orderService;

    public OrdersController( CurrencyService currencyService, OrderService orderService) {
        this.currencyService = currencyService;
        this.orderService = orderService;
    }


    @GetMapping("/orders")
    public String orders(Principal principal, Model model, @CookieValue(value = "currency", required = false) String cookie,
                         HttpServletResponse response, HttpServletRequest request) {

        if (!model.containsAttribute("allOrders")) {
            model.addAttribute("allOrders", orderService.getAllByUser(principal.getName()));
        }

        if (!model.containsAttribute("currDTO")) {
            model.addAttribute("currDTO", currencyService.getCurrencyDTO(request,response,cookie));
        }


        return "orders";
    }


}
