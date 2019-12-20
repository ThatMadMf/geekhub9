package org.geekhub.crypto.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLogger implements Logger {
    private static final Logger consoleLogger = new ConsoleLogger();
    private final Path logDestination;

    public FileLogger(String home) {
        logDestination = Paths.get(home + "/logs.txt");
    }

    @Override
    public void log(String message) {
        printToFile(message);
    }

    @Override
    public void warn(String message) {
        printToFile("WARNING: " + message);
    }

    @Override
    public void error(String message) {
        printToFile("ERROR: " + message);
    }

    private void printToFile(String message) {
        try (OutputStream fileOutputStream = Files.newOutputStream(logDestination);
             PrintStream printStream = new PrintStream(fileOutputStream)) {
            printStream.println(message);
        } catch (IOException e) {
            consoleLogger.warn("Cannot write to file");
        }
    }
}
