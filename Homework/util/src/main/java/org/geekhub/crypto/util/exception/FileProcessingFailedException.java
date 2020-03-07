package org.geekhub.crypto.util.exception;

public class FileProcessingFailedException extends RuntimeException{

    public FileProcessingFailedException(String message) {
        super(message);
    }

    public  FileProcessingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
