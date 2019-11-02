package org.geekhub.crypto.history;

import java.time.LocalDate;

class HistoryRecord {
    private final int INDEX;
    private final Operations OPERATION;
    private final String USER_INPUT;
    private LocalDate OPERATION_DATE;

    public HistoryRecord(int index, Operations operation, String input) {
        INDEX = index;
        OPERATION = operation;
        USER_INPUT = input;
        OPERATION_DATE = LocalDate.now();
    }

    @Override
    public String toString() {
        return INDEX + ". " + OPERATION.toString() + " - " + USER_INPUT + " : " + OPERATION_DATE;
    }

}
