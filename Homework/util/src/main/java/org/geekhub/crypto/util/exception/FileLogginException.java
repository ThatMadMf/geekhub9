package org.geekhub.crypto.util.exception;

import java.io.IOException;

public class FileLogginException extends RuntimeException {
    private final IOException cause;
    private final String massage;

    public FileLogginException(String massage, IOException cause) {
        this.massage = massage;
        this.cause = cause;
    }
}
