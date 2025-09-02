package bg.softuni.bikes_shop.model.dto;


import bg.softuni.bikes_shop.model.validation.annotation.FieldsMisMatching;
import bg.softuni.bikes_shop.model.validation.annotation.PasswordIsFound;
import bg.softuni.bikes_shop.model.validation.annotation.PasswordMatch;
import jakarta.validation.constraints.NotEmpty;


@PasswordIsFound(
        email = "oldEmail",
        password = "currentPassword"
)
@FieldsMisMatching(
        firstField = "newEmail",
        secondField = "confirmEmail"
)
public record UserUpdateEmailDTO(
        @NotEmpty
        String oldEmail,
        @NotEmpty
        String newEmail,
        @NotEmpty
        String confirmEmail,
        @PasswordMatch
        String currentPassword,
        @PasswordMatch
        String newPassword
) {

    public static UserUpdateEmailDTO empty() {
        return
                new UserUpdateEmailDTO(null, null, null,null,null);
    }
}
