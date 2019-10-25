package org.geekhub.crypto.ui;

class HistoryRecord  {
    private final int index;
    private final String operation;
    private final String userInput;

    public HistoryRecord (int index, String operation, String input) {
        this.index = index;
        this.operation = operation;
        this.userInput = input;
    }

    @Override
    public String toString() {
        return index + ". " + operation + " - " + userInput;
    }

}
