package org.geekhub.crypto.history;

import java.io.PrintWriter;
import java.util.List;

public class ResponseHistoryPrinter implements HistoryPrinter {

    private PrintWriter writer;

    public ResponseHistoryPrinter(PrintWriter historyWriter) {
        writer = historyWriter;
    }

    @Override
    public void print(List<HistoryRecord> history) {
        for(HistoryRecord record : history) {
            writer.println(record.toString());
        }
    }
}
