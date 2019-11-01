package org.geekhub.crypto.ui;

import java.util.LinkedList;

class History {
    private final LinkedList<HistoryRecord> historyRecords;

    public LinkedList<HistoryRecord> getHistoryRecords() {
        return historyRecords;
    }

    public History() {
        historyRecords = new LinkedList<>();
    }

    void addToHistory(String operationName, String userInput) {
        historyRecords.add(new HistoryRecord(historyRecords.size(), operationName, userInput));
    }

    void clearHistory() {
        historyRecords.clear();
    }

    void removeLastRecord() {
        historyRecords.pollLast();
    }
}
