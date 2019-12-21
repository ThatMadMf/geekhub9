package org.geekhub.crypto.history;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.exception.EmptyHistoryException;
import org.geekhub.crypto.exception.FileProcessingFailedException;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodingHistory {
    private final LinkedList<HistoryRecord> historyRecords;
    private final HistoryManager historyManager;
    private static Logger compostiteLogger = LoggerFactory.getLogger();

    public List<HistoryRecord> getHistoryRecords() {
        if (historyRecords.isEmpty()) {
            try {
                historyRecords.addAll(readHistory());
            } catch (EmptyHistoryException e) {
                compostiteLogger.log("History is empty");
            }
        }
        return historyRecords;
    }

    public List<HistoryRecord> getHistoryRecords(CodecUsecase usecase) {
        if (historyRecords.isEmpty()) {
            try {
                historyRecords.addAll(readHistory());
            } catch (EmptyHistoryException e) {
                compostiteLogger.log("History is empty");
            }
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

    public CodingHistory(String path) {
        historyRecords = new LinkedList<>();
        historyManager = new HistoryManager(path + "/history.ser");
    }

    public void addToHistory(HistoryRecord record) {
        if (record == null) {
            throw new IllegalArgumentException();
        }
        if (historyRecords.isEmpty()) {
            try {
                historyRecords.addAll(readHistory());
            } catch (EmptyHistoryException e) {
                historyRecords.add(record);
                historyManager.saveHistory(historyRecords);
            }
        } else {
            historyRecords.add(record);
            historyManager.saveHistory(historyRecords);
        }
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
        if (serializedHistory == null || serializedHistory.isEmpty()) {
            throw new EmptyHistoryException("History is empty");
        }
        return serializedHistory;
    }

}
