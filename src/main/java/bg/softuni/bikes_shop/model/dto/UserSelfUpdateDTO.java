package bg.softuni.bikes_shop.model.dto;


import bg.softuni.bikes_shop.model.validation.annotation.FieldsMisMatching;
import bg.softuni.bikes_shop.model.validation.annotation.PasswordIsFound;
import bg.softuni.bikes_shop.model.validation.annotation.PasswordMatch;

import jakarta.validation.constraints.NotEmpty;


@PasswordIsFound(
        email = "oldEmail",
        password = "oldPassword"
)
@FieldsMisMatching(
        firstField = "oldPassword",
        secondField = "newPassword"
)
public record UserSelfUpdateDTO(
        @NotEmpty
        String oldEmail,

        @PasswordMatch
        String oldPassword,

        @PasswordMatch
        String newPassword
) {

    public static UserSelfUpdateDTO empty() {
        return
                new UserSelfUpdateDTO(null, null, null);
    }
}
