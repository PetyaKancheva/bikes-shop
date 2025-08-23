package bg.softuni.bikes_shop.service.impl;

import bg.softuni.bikes_shop.model.dto.CurrencyExchangeDTO;
import bg.softuni.bikes_shop.model.entity.CurrencyEntity;
import bg.softuni.bikes_shop.repository.CurrencyRepository;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {
    private CurrencyServiceImpl serviceToTest;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;

    @Mock
    private CurrencyRepository mockCurrencyRepository;
    @Mock
    private LocaleResolver localeResolver;


    @BeforeEach
    void setUp() {
        serviceToTest = new CurrencyServiceImpl(mockCurrencyRepository, localeResolver);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
    }

    @Test
    void testUpdateLocalBGN() {
        String selectedCurrency = "BGN";

        serviceToTest.updateLocale(mockRequest, mockResponse, selectedCurrency);

        Assertions.assertEquals("bg", mockResponse.getLocale().getLanguage());
    }

    @Test
    void testUpdateCookieNoCookie() {
        String selectedCurrency = "BGN";

        serviceToTest.updateCookie(mockRequest, mockResponse, selectedCurrency);

        assertEquals(selectedCurrency, mockResponse.getCookie("currency").getValue());
    }

    @Test
    void testUpdateCookieWithCookieEURotBGN() {
        String selectedCurrency = "BGN";
        Cookie cookie = new Cookie("currency", "EUR");

        mockRequest.setCookies(cookie);

        serviceToTest.updateCookie(mockRequest, mockResponse, selectedCurrency);

        assertEquals(selectedCurrency, mockResponse.getCookie("currency").getValue());
    }

    @Test
    void testGetCurrencyDTO() {

        CurrencyEntity currencyBGN = new CurrencyEntity().setName("BGN").setRate(BigDecimal.valueOf(1.96));

        when(mockCurrencyRepository.findByName(currencyBGN.getName())).thenReturn(currencyBGN);
        when(localeResolver.resolveLocale(mockRequest)).thenReturn(Locale.ENGLISH);

        CurrencyExchangeDTO result = serviceToTest.getCurrencyDTO(mockRequest, mockResponse, currencyBGN.getName());

        Assertions.assertEquals(currencyBGN.getRate().doubleValue(), result.rate());

    }

}