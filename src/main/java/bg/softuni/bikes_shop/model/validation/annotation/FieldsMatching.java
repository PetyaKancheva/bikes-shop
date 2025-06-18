package bg.softuni.bikes_shop.model.validation.annotation;

import bg.softuni.bikes_shop.model.validation.validator.FieldsMatchingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy= FieldsMatchingValidator.class)
public @interface FieldsMatching {

    String firstField();
    String secondField();
    String message() default "Must match second field";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
