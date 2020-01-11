package org.geekhub.crypto.ui;

import org.geekhub.crypto.logging.FileLogger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;

public class FileLoggerTest {

    private final FileLogger fileLogger = new FileLogger(System.getProperty("java.io.tmpdir"));
    private final Path homePath = Paths.get(System.getProperty("java.io.tmpdir")).resolve("logs.txt");

    @BeforeMethod
    public void initialise() throws IOException {
        Files.deleteIfExists(homePath);
        Files.createFile(homePath);

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