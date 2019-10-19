package org.geekhub.crypto.ui;

import java.util.Arrays;

public class History {
    private String[] array;
    private int operationIndex;

    public History() {
        array = new String[5];
        operationIndex = 1;
    }

    public void showHistory() {
        for (String record : array) {
            if (record == null) {
                break;
            }
            System.out.println(record);
        }
    }

    public void addToHistory(String operationName, String userInput) {
        if (operationIndex == array.length) {
            array = Arrays.copyOf(array, array.length + 5);
        }
        array[operationIndex - 1] = operationIndex + "." + operationName + " - " + userInput;
        operationIndex++;
    }
}
