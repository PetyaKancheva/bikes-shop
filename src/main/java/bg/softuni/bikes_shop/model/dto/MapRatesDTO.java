package bg.softuni.bikes_shop.model.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.Map;

public record MapRatesDTO( String base, Map<String, BigDecimal> rates) {
}
