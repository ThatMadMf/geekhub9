package org.geekhub.crypto.logging;

import java.util.List;

public class CompositeLogger implements Logger {
    private final List<Logger> loggers;

    public CompositeLogger(List<Logger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void log(String message) {
        loggers.forEach(logger -> logger.log(message));
    }

    @Override
    public void warn(String message) {
        loggers.forEach(logger -> logger.warn(message));
    }

    @Override
    public void error(String message) {
        loggers.forEach(logger -> logger.error(message));
    }

    @Override
    public void error(Exception e) {
        loggers.forEach(logger -> logger.error(e));
    }
}
