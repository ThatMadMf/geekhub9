package org.geekhub.crypto.history;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CodingHistoryTest {

    private CodingHistory history;

    @BeforeMethod
    void initialise() {
        history = new CodingHistory();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_AddingNullRecord_Expect_AddingFail() {
        history.addToHistory(null);
    }

    @Test
    void When_AddingNotNullRecord_Expect_Success() {
        history.clearHistory();
        HistoryRecord record = new HistoryRecord(Operation.SHOW_HISTORY);

        history.addToHistory(record);

        assertEquals(history.getHistoryRecords(), List.of(record));
    }

    @Test
    void When_ClearingEmptyHistory_Expect_Success() {
        history.clearHistory();

        assertTrue(history.getHistoryRecords().isEmpty());
    }

    @Test
    void When_ClearingNotEmptyHistory_Expect_Success() {
        history.addToHistory(new HistoryRecord(Operation.ENCODE));
        history.addToHistory(new HistoryRecord(Operation.DECODE));

        history.clearHistory();

        assertTrue(history.getHistoryRecords().isEmpty());
    }

    @Test
    void When_RemovingLastElementWithEmptyHistory_Expect_Success() {
        history.removeLastRecord();

        assertTrue(history.getHistoryRecords().isEmpty());
    }

    @Test
    void When_RemovingLastElementWithOneElementInHistory_Expect_Success() {
        history.addToHistory(new HistoryRecord(Operation.ENCODE));

        history.removeLastRecord();

        assertTrue(history.getHistoryRecords().isEmpty());
    }

    @Test
    void When_RemovingLastElementWithMultipleElementsInHistory_Expect_Success() {
        history.addToHistory(new HistoryRecord(Operation.ENCODE));
        history.addToHistory(new HistoryRecord(Operation.ENCODE));

        history.removeLastRecord();

        assertEquals(history.getHistoryRecords(), List.of(
                new HistoryRecord(Operation.ENCODE))
        );
    }
}