package org.geekhub.crypto.ui;

class HistoryRecord  {
    private final int index;
    private final Operations operation;
    private final String userInput;

    public HistoryRecord (int index, Operations operation, String input) {
        this.index = index;
        this.operation = operation;
        this.userInput = input;
    }

    @Override
    public String toString() {
        return index + ". " + operation.toString() + " - " + userInput;
    }

}
