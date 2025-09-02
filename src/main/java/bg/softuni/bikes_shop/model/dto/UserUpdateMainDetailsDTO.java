package bg.softuni.bikes_shop.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record UserUpdateMainDetailsDTO(
        @NotEmpty
        String currentEmail,
        @Size(min = 3, max = 15, message = "Must be between 3 and 15 characters.")
        String firstName,
        @Size(min = 3, max = 15, message = "Must be between 3 and 15 characters.")
        String lastName,
        @Size(min = 3, message = "Must be at least 3 characters.")
        String address,
        String country

) {
    public static UserUpdateMainDetailsDTO empty() {
        return new UserUpdateMainDetailsDTO(null, null, null, null, null);
    }
}
