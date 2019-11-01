package org.geekhub.crypto.ui;

import java.util.LinkedList;

class HistoryConsolePrinter implements HistoryPrinter {

    @Override
    public void print(LinkedList<HistoryRecord> history) {
        if (history.isEmpty()) {
            System.out.println("History is empty");
            return;
        }
        for (HistoryRecord record : history) {
            System.out.println(record);
        }
    }
}
