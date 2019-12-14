package org.geekhub.crypto.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.testng.Assert.assertEquals;

public class FileLoggerTest {

    private FileLogger fileLogger;
    private Path homePath;

    @BeforeMethod
    public void initialise() throws IOException {
        homePath = Paths.get(System.getProperty("user.home") + "/logs.txt");
        Files.newInputStream(homePath, StandardOpenOption.TRUNCATE_EXISTING);
        fileLogger = new FileLogger(System.getProperty("user.home"));
    }

    @Test
    public void When_LoggingCorrectly_Expect_Success() throws IOException {
        fileLogger.log("Test of logging");

        String readLine = Files.readString(homePath);

        assertEquals(readLine, "Test of logging\n");
    }

    @Test
    public void When_WarningCorrectly_Expect_Success() throws IOException {
        fileLogger.warn("Test of warning");

        String readLine = Files.readString(homePath);

        assertEquals(readLine, "WARNING: Test of warning\n");
    }

    @Test
    public void When_ReportingErrorCorrectly_Expect_Success() throws IOException {
        fileLogger.error("Test of error reporting");

        String readLine = Files.readString(homePath);

        assertEquals(readLine, "ERROR: Test of error reporting\n");
    }
}