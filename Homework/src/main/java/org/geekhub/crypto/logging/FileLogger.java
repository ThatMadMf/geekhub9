package org.geekhub.crypto.logging;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLogger implements Logger {
    private static final Logger consoleLogger = new ConsoleLogger();
    private final Path logDestination;

    public FileLogger(String dir) {
        logDestination = Paths.get(dir + "/logs.txt");
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
        try (OutputStream fileOutputStream = Files.newOutputStream(logDestination, StandardOpenOption.APPEND);
             PrintStream printStream = new PrintStream(fileOutputStream)) {
            printStream.println(message);
        } catch (IOException e) {
            consoleLogger.warn("Cannot write to file");
        }
    }
}
