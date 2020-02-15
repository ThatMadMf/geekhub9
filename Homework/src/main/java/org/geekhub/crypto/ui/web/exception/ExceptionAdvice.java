package org.geekhub.crypto.ui.web.exception;

import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.exception.IllegalInputException;
import org.geekhub.crypto.model.web.ErrorDto;
import org.geekhub.crypto.ui.web.controller.RestHomeController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(assignableTypes = RestHomeController.class)
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CodecUnsupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleCodecUnsupportedException() {
        return new ErrorDto("Invalid codec");
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadInputExceptions() {
        return new ErrorDto("Invalid input");
    }
}
