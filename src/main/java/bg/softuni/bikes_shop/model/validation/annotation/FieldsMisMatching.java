package bg.softuni.bikes_shop.model.validation.annotation;

import bg.softuni.bikes_shop.model.validation.validator.FieldsMatchingValidator;
import bg.softuni.bikes_shop.model.validation.validator.FieldsMisMatchingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy= FieldsMisMatchingValidator.class)
public @interface FieldsMisMatching {

    String firstField();
    String secondField();
    String message() default "Fields must be different";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
