package main.java.org.geekhub.crypto.history;

import java.util.List;

public class HistoryConsolePrinter implements HistoryPrinter {

    @Override
    public void print(List<HistoryRecord> history) {
        if (history.isEmpty()) {
            System.out.println("History is empty");
            return;
        }
        for (HistoryRecord record : history) {
            System.out.println(record);
        }
    }
}
