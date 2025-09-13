package bg.softuni.bikes_shop.model.dto;


import bg.softuni.bikes_shop.model.validation.annotation.FieldsMatching;
import bg.softuni.bikes_shop.model.validation.annotation.FieldsMisMatching;
import bg.softuni.bikes_shop.model.validation.annotation.PasswordIsFound;
import bg.softuni.bikes_shop.model.validation.annotation.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


@PasswordIsFound(
        email = "oldEmail",
        password = "currentPassword"
)
@FieldsMatching(
        firstField = "newEmail",
        secondField = "confirmEmail"
)
public record UserUpdateEmailDTO(
        @NotEmpty
        String oldEmail,
        @Email
        @NotEmpty
        String newEmail,
        @Email
        @NotEmpty
        String confirmEmail,
        @PasswordMatch
        String currentPassword
) {

    public static UserUpdateEmailDTO empty() {
        return
                new UserUpdateEmailDTO(null, null, null,null);
    }
}
