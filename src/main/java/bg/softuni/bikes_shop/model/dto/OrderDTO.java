package bg.softuni.bikes_shop.model.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record OrderDTO(
        @NotNull
        String buyer,
        @NotEmpty
        List<ItemDTO> items,
        @NotNull
        Double totalSum) {

    public static OrderDTO empty() {
        return new OrderDTO(null, null, null);
    }
}
