package bg.softuni.bikes_shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomObjectNotFoundException extends RuntimeException {
    public CustomObjectNotFoundException(String message ){
        super(message);
    }
}