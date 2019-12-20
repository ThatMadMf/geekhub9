package org.geekhub.crypto.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompositeLogger implements Logger {
    private final List<Logger> loggers;

    public CompositeLogger(Set<LogDestination> loggerDestinations) {
        loggers = new ArrayList<>();
        for (LogDestination logger : loggerDestinations) {
            loggers.add(getLogDestination(logger));
        }
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

    private Logger getLogDestination(LogDestination logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger destination is null");
        }
        switch (logger) {
            case FILE:
                return new FileLogger(System.getProperty("user.home"));
            case CONSOLE:
                return new ConsoleLogger();
            default:
                throw new IllegalArgumentException("Unsupported destination");
        }
    }
}
