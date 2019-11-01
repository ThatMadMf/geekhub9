package org.geekhub.crypto.ui;

import java.util.Scanner;

class HistoryMenu {

    static void displayMenu(Scanner scanner, History history) {
        System.out.println("1 - Show History\n2 - Remove last record\n3 - Clear history");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                new HistoryConsolePrinter().print(history.getHistoryRecords());
                history.addToHistory("Show History", "1");
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
