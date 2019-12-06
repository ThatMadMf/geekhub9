package org.geekhub.crypto.ui;

import org.geekhub.crypto.history.HistoryConsolePrinter;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.util.OperationUnsupportedException;

import java.util.Scanner;

class HistoryMenu {

    public static void displayMenu(Scanner scanner, CodingHistory history) {
        System.out.println("1 - Show History\n2 - Remove last record\n3 - Clear history");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                new HistoryConsolePrinter().print(history.getHistoryRecords());
                history.addToHistory(new HistoryRecord(Operation.SHOW_HISTORY, null, null));
                break;
            case "2":
                history.removeLastRecord();
                break;
            case "3":
                history.clearHistory();
                history.addToHistory(new HistoryRecord(Operation.CLEAR_HISTORY, null, null));
                break;
            default:
                throw new OperationUnsupportedException("Operation is not supported");
        }
    }
}
