package bg.softuni.bikes_shop.model.dto;


import bg.softuni.bikes_shop.model.validation.annotation.FieldsMatching;
import bg.softuni.bikes_shop.model.validation.annotation.FieldsMisMatching;
import bg.softuni.bikes_shop.model.validation.annotation.PasswordIsFound;
import bg.softuni.bikes_shop.model.validation.annotation.PasswordMatch;

import jakarta.validation.constraints.NotEmpty;


@PasswordIsFound(
        email = "currentEmail",
        password = "oldPassword"
)
@FieldsMisMatching(
        firstField = "oldPassword",
        secondField = "newPassword"
)
@FieldsMatching(
        firstField = "newPassword",
        secondField = "confirmPassword"
)
public record UserUpdatePasswordDTO(
        @NotEmpty
        String currentEmail,
        @PasswordMatch
        String oldPassword,
        @PasswordMatch
        String newPassword,
        @PasswordMatch
        String confirmPassword

) {

    public static UserUpdatePasswordDTO empty() {
        return
                new UserUpdatePasswordDTO(null, null, null,null);
    }
}
