package org.geekhub.reddit.exception;

import org.h2.jdbc.JdbcSQLDataException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {DataBaseRowException.class, NoRightsException.class})
    public ResponseEntity<Object> handle(RuntimeException ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(value = {RegistrationException.class})
    public ResponseEntity<Object> handleRegistration(RegistrationException ex, WebRequest webRequest) {
        String message = "Cannot register :  " + ex.getMessage();
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(value = {JdbcSQLDataException.class})
    public ResponseEntity<Object> handleDataBaseException(JdbcSQLDataException ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, "Internal error", new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }
}
