package org.geekhub.crypto.web.exception;

import org.geekhub.crypto.coders.exception.IllegalInputException;
import org.geekhub.crypto.web.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadInputExceptions() {
        return new ErrorDto("Invalid input");
    }
}
