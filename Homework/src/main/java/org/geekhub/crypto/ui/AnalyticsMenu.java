package org.geekhub.crypto.ui;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.util.ConsoleLogger;
import org.geekhub.crypto.util.EmptyHistoryException;
import org.geekhub.crypto.util.Logger;
import org.geekhub.crypto.util.OperationUnsupportedException;

import java.util.Scanner;

class AnalyticsMenu {
    public static void displayMenu(Scanner scanner, CodingHistory history) {
        Logger consoleLogger = new ConsoleLogger();
        consoleLogger.log("1 - Count inputs\n2 - Count by date\n3 - The most popular algorithm");
        String input = scanner.nextLine();
        CodingAudit audit = new CodingAudit(history);

        switch (input) {
            case "1":
                consoleLogger.log(audit.countEncodingInputs().toString());
                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            case "2":
                consoleLogger.log("Encoding;");
                consoleLogger.log(audit.countCodingsByDate(CodecUsecase.ENCODING).toString());

                consoleLogger.log("Decoding:");
                consoleLogger.log(audit.countCodingsByDate(CodecUsecase.DECODING).toString());

                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            case "3":
                try {
                    Algorithm encodeCodec = audit.findMostPopularCodec(CodecUsecase.ENCODING);
                    consoleLogger.log("Encoding:");
                    consoleLogger.log(encodeCodec.name());

                    Algorithm decodeCodec = audit.findMostPopularCodec(CodecUsecase.DECODING);
                    consoleLogger.log("Decoding:");
                    consoleLogger.log(decodeCodec.name());
                } catch (EmptyHistoryException e) {
                    consoleLogger.log("Cannot perform operation. History is empty");
                }
                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            default:
                throw new OperationUnsupportedException("Invalid input.");
        }
    }


}
