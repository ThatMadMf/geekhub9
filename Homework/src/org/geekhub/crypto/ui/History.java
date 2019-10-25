package org.geekhub.crypto.ui;

import java.util.ArrayList;
import java.util.List;

public class History implements HistoryPrinter {
    private final List<HistoryRecord> array;
    private int operationIndex;

    public History() {
        array = new ArrayList<>();
        operationIndex = 1;
    }

    @Override
    public void printInConsole() {
        for (HistoryRecord record : array) {
            System.out.println(record);
        }
    }

    void addToHistory(String operationName, String userInput) {
        array.add(new HistoryRecord(operationIndex, operationName, userInput));
        operationIndex++;
    }
}
