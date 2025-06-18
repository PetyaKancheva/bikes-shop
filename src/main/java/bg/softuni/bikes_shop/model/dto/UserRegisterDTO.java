package bg.softuni.bikes_shop.model.dto;

import bg.softuni.bikes_shop.model.validation.annotation.FieldsMatching;
import bg.softuni.bikes_shop.model.validation.annotation.PasswordMatch;
import bg.softuni.bikes_shop.model.validation.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@FieldsMatching(
        firstField = "password",
        secondField = "confirmPassword",
        message = "Passwords should match."
)
public record UserRegisterDTO(
        @NotEmpty(message = "Must be populated.")
        @Email(message = "Must be an e-mail format.")
        @UniqueEmail
        String email,
        @Size(min = 3, max = 15, message = "Must be between 3 and 15 characters.")
        String firstName,
        @Size(min = 3, max = 15, message = "Must be between 3 and 15 characters.")
        String lastName,
        @Size(min = 3, message = "Must be at least 3 characters.")
        String address,
        String country,
        @PasswordMatch
        String password,
        @PasswordMatch
        String confirmPassword) {
    public static UserRegisterDTO empty() {
        return new UserRegisterDTO(null, null, null, null, null, null, null);
    }


}
