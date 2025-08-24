package bg.softuni.bikes_shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomRestException extends HttpClientErrorException {
    public CustomRestException(HttpStatusCode statusCode, String statusText ){
        super( statusCode,  statusText +"custom text ");
    }
}
