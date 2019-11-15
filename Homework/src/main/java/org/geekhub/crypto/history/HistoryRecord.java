package main.java.org.geekhub.crypto.history;

import main.java.org.geekhub.crypto.coders.Algorithm;

import java.time.LocalDate;

public class HistoryRecord {
    private final Operation operation;
    private final String userInput;
    private final Algorithm codec;
    private final LocalDate operationDate;

    public HistoryRecord(Operation operation, String input, Algorithm codec) {
        this.operation = operation;
        this.codec = codec;
        userInput = input;
        operationDate = LocalDate.now();
    }

    public HistoryRecord(Operation operation, String input, Algorithm codec, LocalDate date)  {
        this.operation = operation;
        this.codec = codec;
        userInput = input;
        operationDate = date;
    }

    public HistoryRecord(Operation operation) {
        this.operation = operation;
        operationDate = LocalDate.now();
        userInput = null;
        codec = null;
    }

    public HistoryRecord(Operation operation, LocalDate date) {
        this.operation = operation;
        operationDate = date;
        userInput = null;
        codec = null;
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
        return operation.toString() + " - " + userInput + " : " + operationDate;
    }

}
