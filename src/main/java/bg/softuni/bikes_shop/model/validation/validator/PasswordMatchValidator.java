package bg.softuni.bikes_shop.model.validation.validator;

import bg.softuni.bikes_shop.model.validation.annotation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, String> {
    private final static String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[a-zA-Z]).{6,8}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        } else {
            Pattern pattern = Pattern.compile(PASSWORD_REGEX, Pattern.DOTALL);
            Matcher matcher = pattern.matcher(value);
            return matcher.find();
        }

    }
}
