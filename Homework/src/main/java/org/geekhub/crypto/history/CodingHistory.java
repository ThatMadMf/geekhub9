package org.geekhub.crypto.history;

import org.geekhub.crypto.analytics.CodecUsecase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodingHistory {
    private final LinkedList<HistoryRecord> historyRecords;
    private final HistoryManager historyManager;

    public List<HistoryRecord> getHistoryRecords() {
        if(historyRecords.isEmpty()) {
            historyRecords.addAll(readHistory());
        }
        return historyRecords;
    }

    public List<HistoryRecord> getHistoryRecords(CodecUsecase usecase) {
        if(historyRecords.isEmpty()) {
            historyRecords.addAll(readHistory());
        }
        List<HistoryRecord> result = new ArrayList<>();
        for (HistoryRecord record : historyRecords) {
            if (record.getOperation() == Operation.usecaseToOperation(usecase)) {
                result.add(record);
            }
        }
        return result;
    }

    public CodingHistory() {
        historyRecords = new LinkedList<>();
        historyManager = new HistoryManager("Homework/history.ser");
    }

    public void addToHistory(HistoryRecord record) {
        if (record == null) {
            throw new IllegalArgumentException();
        }
        if(historyRecords.isEmpty()) {
            historyRecords.addAll(readHistory());
        }
        historyRecords.add(record);
        historyManager.saveHistory(historyRecords);
    }

    public void clearHistory() {
        historyRecords.clear();
        historyManager.saveHistory(historyRecords);
    }

    public void removeLastRecord() {
        historyRecords.pollLast();
        historyManager.saveHistory(historyRecords);
    }

    private LinkedList<HistoryRecord> readHistory() {
        LinkedList<HistoryRecord> serializedHistory = historyManager.readHistory();
        if(serializedHistory == null) {
            return new LinkedList<>();
        }
        return serializedHistory;
    }

}
