package org.geekhub.crypto.ui;

import java.util.ArrayList;
import java.util.List;

public class History {
    private final List<HistoryRecord> array;
    private int operationIndex;

    public History() {
        array = new ArrayList<>();
        operationIndex = 1;
    }

    void addToHistory(String operationName, String userInput) {
        array.add(new HistoryRecord(operationIndex, operationName, userInput));
        operationIndex++;
    }

    public void printInConsole() {
        if(array.isEmpty()) {
            System.out.println("History is empty");
            return;
        }
        for (HistoryRecord record : array) {
            System.out.println(record);
        }
    }

    void clearHistory() {
        array.clear();
    }

    void removeLastRecord() {
        array.remove(array.size() - 1);
        operationIndex--;
    }
}
