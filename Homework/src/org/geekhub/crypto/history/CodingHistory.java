package org.geekhub.crypto.history;

import java.util.LinkedList;

public class CodingHistory {
    private final LinkedList<HistoryRecord> historyRecords;

    public LinkedList<HistoryRecord> getHistoryRecords() {
        return historyRecords;
    }

    public CodingHistory() {
        historyRecords = new LinkedList<>();
    }

    public void addToHistory(Operations operationName, String userInput) {
        historyRecords.add(new HistoryRecord(historyRecords.size(), operationName, userInput));
    }

    void clearHistory() {
        historyRecords.clear();
    }

    void removeLastRecord() {
        historyRecords.pollLast();
    }
}
