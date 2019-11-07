package org.geekhub.crypto.ui;

import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryConsolePrinter;
import org.geekhub.crypto.history.Operation;

import java.util.Scanner;

class HistoryMenu {

    public static void displayMenu(Scanner scanner, CodingHistory history) {
        System.out.println("1 - Show History\n2 - Remove last record\n3 - Clear history");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                new HistoryConsolePrinter().print(history.getHistoryRecords());
                history.addToHistory(Operation.SHOW_HISTORY, null, null);
                break;
            case "2":
                history.removeLastRecord();
                break;
            case "3":
                history.clearHistory();
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }
}
