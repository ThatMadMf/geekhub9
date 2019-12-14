package org.geekhub.crypto.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLogger implements Logger {
    private OutputStream fileOutputStream;
    private PrintStream printStream;
    private static final Logger consoleLogger = new ConsoleLogger();

    public FileLogger(String home) {
        try {
            fileOutputStream = Files.newOutputStream(Paths.get(home + "/logs.txt"));
            printStream = new PrintStream(fileOutputStream);
        } catch (IOException e) {
            consoleLogger.error("Cannot create console logger due to IO exception");
        }
    }

    @Override
    public void log(String message) {
        printStream.println(message);
    }

    @Override
    public void warn(String message) {
        printStream.println("WARNING: " + message);
    }

    @Override
    public void error(String message) {
        printStream.println("ERROR: " + message);
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
