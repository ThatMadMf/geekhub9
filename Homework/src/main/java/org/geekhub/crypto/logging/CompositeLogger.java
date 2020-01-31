package org.geekhub.crypto.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeLogger implements Logger {
    private final List<Logger> loggers;

    @Autowired
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
