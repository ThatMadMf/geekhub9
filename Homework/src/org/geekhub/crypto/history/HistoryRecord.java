package org.geekhub.crypto.history;

import org.geekhub.crypto.coders.Algorithm;

import java.time.LocalDate;

public class HistoryRecord {
    private final int index;
    private final Operation operation;
    private final String userInput;
    private final Algorithm codec;
    private final LocalDate operationDate;

    public HistoryRecord(int index, Operation operation, String input, Algorithm codec) {
        this.index = index;
        this.operation = operation;
        this.codec = codec;
        userInput = input;
        operationDate = LocalDate.now();
    }

    public Operation getOperation() {
        return operation;
    }

    public String getUserInput() {
        return userInput;
    }

    public Algorithm getCodec() {
        return codec;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    @Override
    public String toString() {
        return index + ". " + operation.toString() + " - " + userInput + " : " + operationDate;
    }

}
