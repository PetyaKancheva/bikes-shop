package bg.softuni.bikes_shop.model.validation.annotation;

import bg.softuni.bikes_shop.model.validation.validator.PasswordIsFoundValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy= PasswordIsFoundValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PasswordIsFound {
    String email();
    String password();
    String message() default "Old password is incorrect.";
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
