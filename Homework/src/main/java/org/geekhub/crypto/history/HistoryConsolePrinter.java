package org.geekhub.crypto.history;

import org.geekhub.crypto.util.ConsoleLogger;
import org.geekhub.crypto.util.Logger;

import java.util.List;

public class HistoryConsolePrinter implements HistoryPrinter {

    @Override
    public void print(List<HistoryRecord> history) {
        Logger consoleLogger = new ConsoleLogger();
        if (history.isEmpty()) {
            consoleLogger.log("History is empty");
            return;
        }
        for (HistoryRecord record : history) {
            consoleLogger.log(record.toString());
        }
    }
}
