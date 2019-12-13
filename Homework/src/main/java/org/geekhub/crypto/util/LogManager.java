package org.geekhub.crypto.util;

public class LogManager {
    private static final ConsoleLogger consoleLogger = new ConsoleLogger();
    private static final FileLogger fileLogger = new FileLogger();
    public static final String INVALID_INPUT = "Invalid input, try again";

    public static void log(String message) {
        consoleLogger.log(message);
        fileLogger.log(message);
    }


    public static void warn(String message) {
        consoleLogger.warn(message);
        fileLogger.warn(message);
    }


    public static void error(String message) {
        consoleLogger.warn(message);
        fileLogger.warn(message);
    }

    public static void close() {
        fileLogger.closeLogger();
    }


}
