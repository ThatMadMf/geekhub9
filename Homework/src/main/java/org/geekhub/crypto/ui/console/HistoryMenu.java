package org.geekhub.crypto.ui.console;

import org.geekhub.crypto.exception.FileProcessingFailedException;
import org.geekhub.crypto.exception.OperationUnsupportedException;
import org.geekhub.crypto.history.HistoryConsolePrinter;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.util.Scanner;

class HistoryMenu {

    private Logger compositeLogger = LoggerFactory.getLoger();

    public void displayMenu(Scanner scanner) {
        System.out.println("1 - Show History\n2 - Remove last record\n3 - Clear history");
        String input = scanner.nextLine();
        HistoryManager history = new HistoryManager();
        try {
            switch (input) {
                case "1":
                    new HistoryConsolePrinter().print(history.readHistory());
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
        } catch (FileProcessingFailedException e) {
            compositeLogger.error(e);
            System.out.println("Error during saving or reading data");
        }
    }
}
