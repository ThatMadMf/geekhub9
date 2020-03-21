package org.geekhub.reddit.web.rest;

import org.geekhub.reddit.exception.DataBaseRowException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {DataBaseRowException.class})
    public ResponseEntity<Object> handle(DataBaseRowException ex, WebRequest webRequest) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
