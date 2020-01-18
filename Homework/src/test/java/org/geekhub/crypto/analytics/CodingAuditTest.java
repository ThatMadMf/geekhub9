package org.geekhub.crypto.analytics;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.exception.EmptyHistoryException;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CodingAuditTest {

    private CodingAudit codingAudit;
    private List<HistoryRecord> history;

    @BeforeMethod
    public void initialise() {
        history = new LinkedList<>();
        codingAudit = new CodingAudit(history);
    }

    @Test
    void When_CodingHistoryIsEmpty_Expect_EmptyWordCountMap() {
        history.clear();

        Map<String, Integer> actualResult = codingAudit.countEncodingInputs();

        assertTrue(actualResult.isEmpty());
    }

    @Test
    void When_CodingHistoryContainsOneWord_Expect_Success() {
        history.clear();
        history.add(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE));

        Map<String, Integer> actualResult = codingAudit.countEncodingInputs();

        assertEquals(actualResult, Map.ofEntries(
                entry("word", 1)
        ));
    }

    @Test()
    void When_CodingHistoryContainsMultipleWords_Expect_Success() {
        history.clear();
        history.add(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE));
        history.add(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));
        history.add(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));

        Map<String, Integer> actualResult = codingAudit.countEncodingInputs();

        assertEquals(actualResult, Map.ofEntries(
                entry("geekhub", 2),
                entry("word", 1)
        ));
    }

    @Test
    void When_CodingHistoryIsEmpty_Expect_EmptyDateCountMap() {
        history.clear();

        Map<LocalDate, Long> actualResult = codingAudit.countCodingsByDate(CodecUsecase.ENCODING);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    void When_CodingHistoryContainsOneDate_Expect_Success() {
        history.clear();
        history.add(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE, LocalDate.now()));

        Map<LocalDate, Long> actualResult = codingAudit.countCodingsByDate(CodecUsecase.ENCODING);
        Map<LocalDate, Long> expectedResult = Map.ofEntries(
                entry(LocalDate.now(), 1L)
        );

        assertEquals(actualResult, expectedResult);
    }

    @Test
    void When_CodingHistoryContainsMultipleDates_Expect_Success() {
        history.clear();
        history.add(new HistoryRecord(Operation.ENCODE, "word", Algorithm.VIGENERE, LocalDate.now()));
        history.add(new HistoryRecord(Operation.ENCODE, "word", Algorithm.MORSE, LocalDate.now()));
        history.add(
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
        history.clear();
        codingAudit.findMostPopularCodec(CodecUsecase.ENCODING);
    }

    @Test
    void When_CodingHistoryContainsOneCodecUsecase_Expect_Success() {
        history.clear();
        history.add(new HistoryRecord(Operation.DECODE, "word", Algorithm.VIGENERE));
        history.add(new HistoryRecord(Operation.ENCODE, "word", Algorithm.CAESAR));


        Algorithm actualResult = codingAudit.findMostPopularCodec(CodecUsecase.ENCODING);

        assertEquals(actualResult, Algorithm.CAESAR);
    }

    @Test
    void When_CodingHistoryContainsMultipleCodesUsecases_Expect_Succcess() {
        List<HistoryRecord> history = new LinkedList<>();

        history.add(new HistoryRecord(Operation.ENCODE, "word", Algorithm.VIGENERE));
        history.add(new HistoryRecord(Operation.ENCODE, "word", Algorithm.CAESAR));
        history.add(new HistoryRecord(Operation.DECODE, "word", Algorithm.CAESAR));
        history.add(new HistoryRecord(Operation.DECODE, "word", Algorithm.CAESAR));
        history.add(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));
        history.add(new HistoryRecord(Operation.DECODE, "geekhub", Algorithm.MORSE));
        history.add(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));
        history.add(new HistoryRecord(Operation.DECODE, "geekhub", Algorithm.MORSE));
        history.add(new HistoryRecord(Operation.ENCODE, "geekhub", Algorithm.MORSE));
        history.add(new HistoryRecord(Operation.DECODE, "geekhub", Algorithm.MORSE));

        CodingAudit codingAudit = new CodingAudit(history);

        Algorithm actualResult = codingAudit.findMostPopularCodec(CodecUsecase.DECODING);

        assertEquals(actualResult, Algorithm.MORSE);
    }
}