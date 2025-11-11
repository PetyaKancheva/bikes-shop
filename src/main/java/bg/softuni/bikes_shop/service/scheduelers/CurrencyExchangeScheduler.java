package bg.softuni.bikes_shop.service.scheduelers;

import bg.softuni.bikes_shop.configuration.properties.ExchangeRateConfigProperties;
import bg.softuni.bikes_shop.model.dto.MapRatesDTO;
import bg.softuni.bikes_shop.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;


@Component
public class CurrencyExchangeScheduler {
    private final ExchangeRateConfigProperties exchangeRateConfigProperties;
    private final RestTemplate restTemplate;
    private final CurrencyService currencyService;
    private static final Logger logger = LoggerFactory.getLogger( CurrencyExchangeScheduler.class);

    public CurrencyExchangeScheduler(ExchangeRateConfigProperties exchangeRateConfigProperties, RestTemplate restTemplate, CurrencyService currencyService) {
        this.exchangeRateConfigProperties = exchangeRateConfigProperties;
        this.restTemplate = restTemplate;
        this.currencyService = currencyService;
    }

//        @Scheduled(cron ="0 */3 */1 * * *")// every 3 min
    @Scheduled(cron = " 0 5 8 */1 * *")
    //    every day     at 8:05
//    @Scheduled(fixedRate=86_400_000) // 24hrs
    public void refreshRates() {
        if (exchangeRateConfigProperties.isEnabled()) {

            MapRatesDTO mapRatesDTO = restTemplate.getForObject(compileURL(), MapRatesDTO.class);
            currencyService.add(mapRatesDTO);

            logger.info("*** Updating exchange rate ***");
        }
    }

    private String compileURL() {
        return String.valueOf(new StringBuilder()
                .append(exchangeRateConfigProperties.getSchema())
                .append("://")
                .append(exchangeRateConfigProperties.getHost())
                .append(exchangeRateConfigProperties.getPath())
                .append("?base=EUR&symbols=")
                .append(String.join(",", exchangeRateConfigProperties.getSymbols())));
    }
}
