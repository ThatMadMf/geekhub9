package org.geekhub.crypto.util.logging;

public interface Logger {

    void log(String message);

    void warn(String message);

    void error(String message);

    void error(Exception e);
}
