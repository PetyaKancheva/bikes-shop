package bg.softuni.bikes_shop.controller;

import bg.softuni.bikes_shop.service.CurrencyService;
import bg.softuni.bikes_shop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

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
