package org.geekhub.crypto.util;

public class IllegalCharacterException extends RuntimeException {
    public IllegalCharacterException() {
        super("Unsupportable character");
    }

    public IllegalCharacterException(String message) {
        super(message);
    }

    public IllegalCharacterException(String message, Throwable cause) {
        super(message, cause);
    }
}
