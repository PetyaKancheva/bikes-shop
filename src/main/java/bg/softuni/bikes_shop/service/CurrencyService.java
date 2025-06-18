package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.dto.CurrencyExchangeDTO;
import bg.softuni.bikes_shop.model.dto.MapRatesDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CurrencyService {
    void add(MapRatesDTO mapRatesDTO);

    void updateLocale(HttpServletRequest request, HttpServletResponse response, String selectedCurrency);

    void updateCookie(HttpServletRequest request, HttpServletResponse response, String selectedCurrency);

    CurrencyExchangeDTO getCurrencyDTO(HttpServletRequest request,HttpServletResponse response,String cookie);
}
