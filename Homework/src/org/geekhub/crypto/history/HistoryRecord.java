package org.geekhub.crypto.history;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.coders.Algorithm;

import java.time.LocalDate;

public class HistoryRecord {
    private final int index;
    private final CodecUsecase operation;
    private final String userInput;
    private final Algorithm codec;
    private final LocalDate operationDate;

    public CodecUsecase getOperation() {
        return operation;
    }

    public String getUserInput() {
        return userInput;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public Algorithm getCodec() {
        return codec;
    }

    public HistoryRecord(int index, CodecUsecase operation, String input, Algorithm codec) {
        this.index = index;
        this.operation = operation;
        this.codec = codec;
        userInput = input;
        operationDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return index + ". " + operation.toString() + " - " + userInput + " : " + operationDate;
    }

}
