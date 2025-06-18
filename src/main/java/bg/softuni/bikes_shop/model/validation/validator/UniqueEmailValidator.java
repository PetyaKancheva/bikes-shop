package bg.softuni.bikes_shop.model.validation.validator;

import bg.softuni.bikes_shop.model.validation.annotation.UniqueEmail;
import bg.softuni.bikes_shop.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else {
            return  userService.isUniqueEmail(value);
        }
    }
}
