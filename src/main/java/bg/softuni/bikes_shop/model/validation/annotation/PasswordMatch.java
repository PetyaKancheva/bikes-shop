package bg.softuni.bikes_shop.model.validation.annotation;

import bg.softuni.bikes_shop.model.validation.validator.PasswordMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy= PasswordMatchValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PasswordMatch {
    String message() default "Password must be between 6 and 8 characters and contain at least one digit.";
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
