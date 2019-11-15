package main.java.org.geekhub.crypto.analytics;

import main.java.org.geekhub.crypto.coders.Algorithm;
import main.java.org.geekhub.crypto.history.CodingHistory;
import main.java.org.geekhub.crypto.history.HistoryRecord;
import main.java.org.geekhub.crypto.history.Operation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Map;

import static java.util.Map.entry;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CodingAuditTest {

    private CodingAudit codingAudit;
    private CodingHistory history;

    @BeforeMethod
    void initialise() {
        codingAudit = new CodingAudit(new CodingHistory());
        history = codingAudit.getCodingHistory();
    }

    @Test
    void When_CodingHistoryIsEmpty_Expect_Empty_WordCountMap() {
        Map actualResult = codingAudit.countEncodingInputs();

        assertTrue(actualResult.isEmpty());
    }

    @Test
    void When_CodingHistoryContainsOneWord_Expect_Success() {
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE));

        Map actualResult = codingAudit.countEncodingInputs();

        assertEquals(actualResult, Map.ofEntries(
                entry("word", 1)
        ));
    }

    @Test()
    void When_CodingHistoryContainsMultipleWords_Expect_Success() {
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));

        Map actualResult = codingAudit.countEncodingInputs();

        assertEquals(actualResult, Map.ofEntries(
                entry("geekhub", 2),
                entry("word", 1)
        ));
    }

    @Test
    void When_CodingHistoryIsEmpty_Expect_EmptyDateCountMap() {
        Map actualResult = codingAudit.countCodingsByDate(CodecUsecase.ENCODING);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    void When_CodingHistoryContainsOneDate_Expect_Success() {
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE, LocalDate.now()));

        Map actualResult = codingAudit.countCodingsByDate(CodecUsecase.ENCODING);
        Map expectedResult = Map.ofEntries(
                entry(LocalDate.now(), 1L)
        );
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void When_CodingHistoryContainsMultipleDates_Expect_Success() {
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.VIGENERE, LocalDate.now()));
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE, LocalDate.now()));
        history.addToHistory(
                new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE, LocalDate.now().plusDays(1))
        );

        Map actualResult = codingAudit.countCodingsByDate(CodecUsecase.ENCODING);
        Map expectedResult = Map.ofEntries(
                entry(LocalDate.now(), 2L),
                entry(LocalDate.now().plusDays(1), 1L)
        );

        assertEquals(actualResult, expectedResult);
    }

    @Test
    void When_CodingHistoryEmpty_Expect_Null() {
        Algorithm actualResult = codingAudit.findMostPopularCodec(CodecUsecase.ENCODING);

        assertEquals(actualResult, null);
    }

    @Test
    void When_CodingHistoryContainsOneCodecUsecase_Expect_Success() {
        history.addToHistory(new HistoryRecord(Operation.ENCODE, "word", Algorithm.VIGENERE));
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

        Algorithm actualResult = codingAudit.findMostPopularCodec(CodecUsecase.DECODING);

        assertEquals(actualResult, Algorithm.CAESAR);
    }
}