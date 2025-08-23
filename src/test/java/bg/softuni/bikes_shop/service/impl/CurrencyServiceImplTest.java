package bg.softuni.bikes_shop.service.impl;

import bg.softuni.bikes_shop.repository.CurrencyRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {
    private CurrencyServiceImpl serviceToTest;
    @Mock
    private CurrencyRepository mockCurrencyRepository;
    @Mock
    private LocaleResolver localeResolver;

    @BeforeEach
    void setUp() {
        serviceToTest = new CurrencyServiceImpl(mockCurrencyRepository, localeResolver);

    }

    @Test
    void testUpdateLocalBGN() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String selectedCurrency = "BGN";

        serviceToTest.updateLocale(request,response,selectedCurrency);

        Assertions.assertEquals("bg",response.getLocale().getLanguage());
    }
    @Test
    void testUpdateCookieNoCookie(){

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String selectedCurrency = "BGN";

        serviceToTest.updateCookie(request,response,selectedCurrency);

        Assertions.assertEquals(selectedCurrency,response.getCookie("currency").getValue());
    }
    @Test
    void testUpdateCookieWithCookieEURotBGN(){
        String selectedCurrency = "BGN";
        MockHttpServletRequest request = new MockHttpServletRequest();
        Cookie cookie= new Cookie("currency", "EUR");

        MockHttpServletResponse response = new MockHttpServletResponse();

       request.setCookies(cookie);

        serviceToTest.updateCookie(request,response,selectedCurrency);

        Assertions.assertEquals(selectedCurrency,response.getCookie("currency").getValue());
    }
}