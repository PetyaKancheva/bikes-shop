package bg.softuni.bikes_shop.model.dto;

import bg.softuni.bikes_shop.model.validation.annotation.PasswordMatch;
import bg.softuni.bikes_shop.model.validation.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserAdminUpdateDTO(
        @NotEmpty
        String oldEmail,
        @NotEmpty(message = "Must be populated.")
        @Email(message = "Must be an e-mail format.")
        @UniqueEmail
        String newEmail,
        @NotEmpty
        List<String> roles,
        @PasswordMatch
        String newPassword,
        @Size(min = 3, max = 15, message = "Must be between 3 and 15 characters.")
        String firstName,
        @Size(min = 3, max = 15, message = "Must be between 3 and 15 characters.")
        String lastName,
        @Size(min = 3, message = "Must be at least 3 characters.")
        String address,
        String country) {


    public static UserAdminUpdateDTO empty() {
        return
                new UserAdminUpdateDTO(null, null, null,null,null,null
                ,null, null);
    }
}
