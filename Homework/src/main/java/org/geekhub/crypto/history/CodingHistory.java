package org.geekhub.crypto.history;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.exception.EmptyHistoryException;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodingHistory {
    private final LinkedList<HistoryRecord> historyRecords;
    private final HistoryManager historyManager;
    private static final Logger compositeLogger = LoggerFactory.getLogger();
    private static final String EMPTY_HISTORY = "History is empty";

    public List<HistoryRecord> getHistoryRecords() {
        return historyManager.readHistory();
    }

    public List<HistoryRecord> getHistoryRecords(CodecUsecase usecase) {
        if (historyRecords.isEmpty()) {
            try {
                historyRecords.addAll(readHistory());
            } catch (EmptyHistoryException e) {
                compositeLogger.log(EMPTY_HISTORY);
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
        historyManager = new HistoryManager();
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
        if(historyRecords.isEmpty()){
            try {
                historyRecords.addAll(readHistory());
            } catch (EmptyHistoryException e) {
                compositeLogger.log(EMPTY_HISTORY);
            }
        }
        historyRecords.pollLast();
        historyManager.saveHistory(historyRecords);
    }

    private LinkedList<HistoryRecord> readHistory() {
        LinkedList<HistoryRecord> serializedHistory = new LinkedList<>(historyManager.readHistory());
        if (serializedHistory.isEmpty()) {
            throw new EmptyHistoryException(EMPTY_HISTORY);
        }
        return serializedHistory;
    }

}
