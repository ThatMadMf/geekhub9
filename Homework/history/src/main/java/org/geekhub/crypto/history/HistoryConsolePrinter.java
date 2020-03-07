package org.geekhub.crypto.history;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoryConsolePrinter implements HistoryPrinter {

    @Override
    public void print(List<HistoryRecord> history) {
        if (history.isEmpty()) {
            System.out.println("History is empty");
            return;
        }
        for (HistoryRecord record : history) {
            System.out.println(record.toString());
        }
    }
}
