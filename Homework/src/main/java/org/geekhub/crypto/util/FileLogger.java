package org.geekhub.crypto.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLogger implements Logger {
    private OutputStream fileOutputStream;
    private PrintStream printStream;
    private static final Logger consoleLogger = new ConsoleLogger();
    private static final String WARNING_MESSAGE = "Error during writing to log file";

    public FileLogger() {
        try {
            fileOutputStream = Files.newOutputStream(Paths.get("Homework/logs.txt").toAbsolutePath());
            printStream = new PrintStream(fileOutputStream);
        } catch (IOException e) {
            consoleLogger.error("Cannot create console logger due to IO exception");
        }
    }

    @Override
    public void log(String message) {
        try {
            printStream.println(message);
        } catch (Exception e) {
            consoleLogger.warn(WARNING_MESSAGE);
        }
    }

    @Override
    public void warn(String message) {
        try {
            printStream.println(message);
        } catch (Exception e) {
            consoleLogger.warn(WARNING_MESSAGE);
        }
    }

    @Override
    public void error(String message) {
        try {
            printStream.println(message);
        } catch (Exception e) {
            consoleLogger.warn(WARNING_MESSAGE);
        }
    }

    public void closeLogger() {
        try {
            fileOutputStream.close();
            printStream.close();
        } catch (IOException e) {
            consoleLogger.error("Cannot close log file");
        }
    }
}
