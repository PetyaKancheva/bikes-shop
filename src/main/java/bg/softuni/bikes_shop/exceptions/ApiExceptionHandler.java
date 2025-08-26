package bg.softuni.bikes_shop.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleHttpClientErrorException(final HttpClientErrorException ex) {

        Logger logger= LoggerFactory.getLogger(Logger.class);

        logger.info("handle handleHttpClientErrorException:" +ex.getMessage() ,ex);

        return new ModelAndView("error/404");
    }

//    @ExceptionHandler(NullPointerException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ModelAndView handleNullPointerExceptionException(final NullPointerException ex) {
//
//        Logger logger= LoggerFactory.getLogger(Logger.class);
//
//        logger.info("handle NullPointerExceptionException:" +ex.getMessage() ,ex);
//
//        return new ModelAndView("error/404");
//    }
}
