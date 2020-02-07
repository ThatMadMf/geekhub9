package org.geekhub.crypto.logging;

import org.geekhub.crypto.exception.FileProcessingFailedException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

public class FileLogger implements Logger {
    private final Path logDestination;

    public FileLogger() {
        logDestination = Paths.get(System.getProperty("user.home")).resolve("logs.txt");
    }

    public FileLogger(Path path) {
        logDestination = path.resolve("logs.txt");
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

    @Override
    public void error(Exception e) {
        String logDivider = "####################";
        String partDivider = "\n";
        printToFile(logDivider);
        printToFile("ERROR" + e.getMessage() + partDivider);
        printToFile("DATE " + LocalDate.now() + partDivider);
        printToFile(e.getCause() + partDivider);
    }

    private void printToFile(String message) {
        try (OutputStream fileOutputStream = Files.newOutputStream(logDestination, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
             PrintStream printStream = new PrintStream(fileOutputStream)) {
            printStream.println(message);
        } catch (IOException e) {
            throw new FileProcessingFailedException("Failed to write to file", e);
        }
    }
}
