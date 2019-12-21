package org.geekhub.crypto.ui;

import org.geekhub.crypto.exception.FileProcessingFailedException;
import org.geekhub.crypto.exception.OperationUnsupportedException;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryConsolePrinter;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.logging.LoggerFactory;
import org.geekhub.crypto.logging.Logger;

import java.util.Scanner;

class HistoryMenu {

    public static void displayMenu(Scanner scanner, CodingHistory history) {
        Logger compositeLogger = LoggerFactory.getLogger();
        System.out.println("1 - Show History\n2 - Remove last record\n3 - Clear history");
        String input = scanner.nextLine();
        try {
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
        } catch (FileProcessingFailedException e) {
            compositeLogger.error("Cannot write or read to history.ser file");
            compositeLogger.error(e.getMessage());
            compositeLogger.error(e.getCause().toString());
            System.out.println("Error during saving or reading data");
        }
    }
}
