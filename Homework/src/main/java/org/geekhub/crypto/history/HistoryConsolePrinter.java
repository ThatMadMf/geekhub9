package org.geekhub.crypto.history;

import org.geekhub.crypto.util.LogManager;

import java.util.List;

public class HistoryConsolePrinter implements HistoryPrinter {

    @Override
    public void print(List<HistoryRecord> history) {
        if (history.isEmpty()) {
            LogManager.log("History is empty");
            return;
        }
        for (HistoryRecord record : history) {
            LogManager.log(record.toString());
        }
    }
}
