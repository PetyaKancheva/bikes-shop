package bg.softuni.bikes_shop.service.impl;

import bg.softuni.bikes_shop.model.dto.CurrencyExchangeDTO;
import bg.softuni.bikes_shop.model.dto.MapRatesDTO;
import bg.softuni.bikes_shop.model.entity.CurrencyEntity;
import bg.softuni.bikes_shop.repository.CurrencyRepository;
import bg.softuni.bikes_shop.service.CurrencyService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final LocaleResolver localeResolver;
    private final static String EUR = "EUR";
    private final static String BGN = "BGN";
    private final static String PLN = "PLN";
    private final static String USD = "USD";


    public CurrencyServiceImpl(CurrencyRepository currencyRepository, LocaleResolver localeResolver) {
        this.currencyRepository = currencyRepository;
        this.localeResolver = localeResolver;
    }

    @Override
    public void add(MapRatesDTO mapRatesDTO) {

        CurrencyEntity ceBGN = new CurrencyEntity();
        ceBGN.setName(BGN);
        ceBGN.setRate(mapRatesDTO.rates().get(BGN));
        currencyRepository.save(ceBGN);

        CurrencyEntity ceUSD = new CurrencyEntity();
        ceUSD.setName(USD);
        ceUSD.setRate(mapRatesDTO.rates().get(USD));
        currencyRepository.save(ceUSD);

        CurrencyEntity cePLN = new CurrencyEntity();
        cePLN.setName(PLN);
        cePLN.setRate(mapRatesDTO.rates().get(PLN));
        currencyRepository.save(cePLN);
    }


    @Override
    public void updateLocale(HttpServletRequest request, HttpServletResponse response, String selectedCurrency) {
        Locale newLocale = null;
        switch (selectedCurrency) {
            case BGN -> newLocale = new Locale.Builder().setLanguage("bg").setRegion("BG").build();
            case PLN -> newLocale = new Locale.Builder().setLanguage("pl").setRegion("PL").build();
            case USD -> newLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
            case EUR -> newLocale = new Locale.Builder().setLanguage("de").setRegion("DE").build();

        }
        localeResolver.setLocale(request, response, newLocale);
    }

    @Override
    public void updateCookie(HttpServletRequest request, HttpServletResponse response, String selectedCurrency) {
        Cookie[] cookies = request.getCookies();

        Cookie currencyCookie = null;
        if (cookies != null) {
            if (Arrays.stream(cookies).anyMatch(cookie -> cookie.getName().equals("currency"))) {
                currencyCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("currency")).findFirst().get();
                currencyCookie.setValue(selectedCurrency);

            }
        }
        if (currencyCookie == null) {
            currencyCookie = new Cookie("currency", selectedCurrency);
            currencyCookie.setHttpOnly(true);
            currencyCookie.setMaxAge(604800000);
        }

        response.addCookie(currencyCookie);
    }

    @Override
    public CurrencyExchangeDTO getCurrencyDTO(HttpServletRequest request, HttpServletResponse response, String cookie) {
        if (cookie != null) {
            Locale currentLocale = localeResolver.resolveLocale(request);
            compareLocale(request, response, currentLocale, cookie);
        }


        if (cookie == null || cookie.equals(EUR)) {
            return new CurrencyExchangeDTO(EUR, 1d);
        } else if (cookie.equals(BGN) || cookie.equals(USD) || cookie.equals(PLN)) {
            return new CurrencyExchangeDTO(cookie, currencyRepository.findByName(cookie).getRate().doubleValue());
        } else {
            return new CurrencyExchangeDTO(EUR, 1d);
        }

    }

    private void compareLocale(HttpServletRequest request, HttpServletResponse response, Locale currentLocale
            , String cookie) {
        if (currentLocale.getCountry().equals("DE") && !cookie.equals("EUR")) {
            updateLocale(request, response, cookie);
        }
    }

}
