package org.geekhub.crypto.logging;

public interface Logger {

    void log(String message);

    void warn(String message);

    void error(String message);
}
