package org.geekhub.crypto.history;

import org.geekhub.crypto.coders.Algorithm;

import java.time.LocalDate;
import java.util.Objects;

public class HistoryRecord {
    private final Operation operation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryRecord record = (HistoryRecord) o;
        return operation == record.operation &&
                Objects.equals(userInput, record.userInput) &&
                codec == record.codec &&
                Objects.equals(operationDate, record.operationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, userInput, codec, operationDate);
    }

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
