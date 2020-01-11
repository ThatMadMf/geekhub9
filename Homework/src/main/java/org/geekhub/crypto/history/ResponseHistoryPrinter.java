package org.geekhub.crypto.history;

import java.io.PrintWriter;
import java.util.List;

public class ResponseHistoryPrinter implements HistoryPrinter {

    private final PrintWriter writer;

    public ResponseHistoryPrinter(PrintWriter historyWriter) {
        writer = historyWriter;
    }

    @Override
    public void print(List<HistoryRecord> history) {
        String div = " / ";
        writer.println("Operation / Date / Codec  / Input<br>");
        for(HistoryRecord record : history) {
            writer.println(record.getOperation() + div + record.getOperationDate() +
                    div + record.getCodec() + div + record.getUserInput() + "<br>");
        }
    }
}
