package org.geekhub.crypto.history;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.coders.Algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodingHistory {
    private final LinkedList<HistoryRecord> historyRecords;

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
        historyRecords = new LinkedList<>();
    }

    public void addToHistory(Operation operationName, String userInput, Algorithm codec) {
        historyRecords.add(new HistoryRecord(historyRecords.size(), operationName, userInput, codec));
    }

    public void clearHistory() {
        historyRecords.clear();
    }

    public void removeLastRecord() {
        historyRecords.pollLast();
    }

}
