package org.geekhub.crypto.exception;

public class CodecUnsupportedException extends RuntimeException {

    private String algorithm;

    public CodecUnsupportedException(String message) {
        super(message);
    }

    public CodecUnsupportedException(String message, String algorithm) {
        super(message);
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
