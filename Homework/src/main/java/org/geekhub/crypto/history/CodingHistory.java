package org.geekhub.crypto.history;

import org.geekhub.crypto.analytics.CodecUsecase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodingHistory {
    private final LinkedList<HistoryRecord> historyRecords;
    private HistoryManager manager = new HistoryManager();

    public List<HistoryRecord> getHistoryRecords() {
        return historyRecords;
    }

    public List<HistoryRecord> getHistoryRecords(CodecUsecase usecase) {
        List<HistoryRecord> result = new ArrayList<>();
        for (HistoryRecord record : historyRecords) {
            if (record.getOperation() == Operation.usecaseToOperation(usecase)) {
                result.add(record);
            }
        }
        return result;
    }

    public CodingHistory() {
        historyRecords = new LinkedList<>(manager.readHistory());
    }

    public CodingHistory(List<HistoryRecord> records) {
        historyRecords = new LinkedList<>(records);
    }

    public void addToHistory(HistoryRecord record) {
        if (record == null) {
            throw new IllegalArgumentException();
        }
        historyRecords.add(record);
        manager.saveRecord(record);
    }

    public void clearHistory() {
        historyRecords.clear();
        manager.clear();
    }

    public void removeLastRecord() {
        historyRecords.pollLast();
        manager.removeLast();
    }

}
