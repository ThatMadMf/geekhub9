package org.geekhub.crypto.analytics;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.exception.EmptyHistoryException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;

import static java.util.Map.entry;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CodingAuditTest {

    private CodingAudit codingAudit;
    private CodingHistory history;

    @BeforeMethod
    public void initialise() throws IOException {
        Files.deleteIfExists(Paths.get(System.getProperty("user.home") + "/history.ser"));
        history = new CodingHistory();
        codingAudit = new CodingAudit(history);
    }

    @Test
    void When_CodingHistoryIsEmpty_Expect_EmptyWordCountMap() {
        history.clearHistory();

        Map<String, Integer> actualResult = codingAudit.countEncodingInputs();

        assertTrue(actualResult.isEmpty());
    }

    @Test
    void When_CodingHistoryContainsOneWord_Expect_Success() {
        history.clearHistory();
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE));

        Map<String, Integer> actualResult = codingAudit.countEncodingInputs();

        assertEquals(actualResult, Map.ofEntries(
                entry("word", 1)
        ));
    }

    @Test()
    void When_CodingHistoryContainsMultipleWords_Expect_Success() {
        history.clearHistory();
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));

        Map<String, Integer> actualResult = codingAudit.countEncodingInputs();

        assertEquals(actualResult, Map.ofEntries(
                entry("geekhub", 2),
                entry("word", 1)
        ));
    }

    @Test
    void When_CodingHistoryIsEmpty_Expect_EmptyDateCountMap() {
        history.clearHistory();

        Map<LocalDate, Long> actualResult = codingAudit.countCodingsByDate(CodecUsecase.ENCODING);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    void When_CodingHistoryContainsOneDate_Expect_Success() {
        history.clearHistory();
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE, LocalDate.now()));

        Map<LocalDate, Long> actualResult = codingAudit.countCodingsByDate(CodecUsecase.ENCODING);
        Map<LocalDate, Long> expectedResult = Map.ofEntries(
                entry(LocalDate.now(), 1L)
        );

        assertEquals(actualResult, expectedResult);
    }

    @Test
    void When_CodingHistoryContainsMultipleDates_Expect_Success() {
        history.clearHistory();
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.VIGENERE, LocalDate.now()));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE, LocalDate.now()));
        history.addToHistory(
                new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE, LocalDate.now().plusDays(1))
        );

        Map<LocalDate, Long> actualResult = codingAudit.countCodingsByDate(CodecUsecase.ENCODING);
        Map<LocalDate, Long> expectedResult = Map.ofEntries(
                entry(LocalDate.now(), 2L),
                entry(LocalDate.now().plusDays(1), 1L)
        );

        assertEquals(actualResult, expectedResult);
    }

    @Test(expectedExceptions = EmptyHistoryException.class)
    void When_CodingHistoryEmpty_Expect_Exception() {
        history.clearHistory();
        codingAudit.findMostPopularCodec(CodecUsecase.ENCODING);
    }

    @Test
    void When_CodingHistoryContainsOneCodecUsecase_Expect_Success() {
        history.clearHistory();
        history.addToHistory(new HistoryRecord(Operation.DECODE, "word", Algorithm.VIGENERE));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.CAESAR));


        Algorithm actualResult = codingAudit.findMostPopularCodec(CodecUsecase.ENCODING);

        assertEquals(actualResult, Algorithm.CAESAR);
    }

    @Test
    void When_CodingHistoryContainsMultipleCodesUsecases_Expect_Succcess() {
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.VIGENERE));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.CAESAR));
        history.addToHistory(new HistoryRecord(Operation.DECODE, "word", Algorithm.CAESAR));
        history.addToHistory(new HistoryRecord(Operation.DECODE, "word", Algorithm.CAESAR));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));
        history.addToHistory(new HistoryRecord(Operation.DECODE, "geekhub", Algorithm.MORSE));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));
        history.addToHistory(new HistoryRecord(Operation.DECODE, "geekhub", Algorithm.MORSE));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));
        history.addToHistory(new HistoryRecord(Operation.DECODE, "geekhub", Algorithm.MORSE));

        Algorithm actualResult = codingAudit.findMostPopularCodec(CodecUsecase.DECODING);

        assertEquals(actualResult, Algorithm.MORSE);
    }
}