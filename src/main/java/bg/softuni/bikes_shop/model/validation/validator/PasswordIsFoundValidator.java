package bg.softuni.bikes_shop.model.validation.validator;


import bg.softuni.bikes_shop.model.validation.annotation.PasswordIsFound;
import bg.softuni.bikes_shop.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class PasswordIsFoundValidator implements ConstraintValidator<PasswordIsFound, Object> {
    private final UserService userService;
    private String email;
    private String password;
    private String message;

    public PasswordIsFoundValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(PasswordIsFound constraintAnnotation) {
       this.email=constraintAnnotation.email();
        this.password = constraintAnnotation.password();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
        Object emailProperty=beanWrapper.getPropertyValue(this.email);
        Object passwordProperty=beanWrapper.getPropertyValue(this.password);

        boolean isValid=userService.isPasswordCorrect(String.valueOf(emailProperty), String.valueOf(passwordProperty));
        if (!isValid) {
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(password)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return  isValid;
    }
}

