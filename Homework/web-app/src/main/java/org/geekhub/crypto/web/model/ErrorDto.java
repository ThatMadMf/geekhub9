package org.geekhub.crypto.web.model;

import org.springframework.http.HttpStatus;

public class ErrorDto {

    private  HttpStatus status;
    private final String message;

    public ErrorDto(String message) {
        this.message = message;
    }

    public ErrorDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
