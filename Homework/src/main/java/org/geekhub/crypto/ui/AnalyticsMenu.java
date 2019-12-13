package org.geekhub.crypto.ui;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.util.*;

import java.util.Scanner;

class AnalyticsMenu {
    public static void displayMenu(Scanner scanner, CodingHistory history) {
        LogManager.log("1 - Count inputs\n2 - Count by date\n3 - The most popular algorithm");
        String input = scanner.nextLine();
        CodingAudit audit = new CodingAudit(history);

        switch (input) {
            case "1":
                LogManager.log(audit.countEncodingInputs().toString());
                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            case "2":
                LogManager.log("Encoding;");
                LogManager.log(audit.countCodingsByDate(CodecUsecase.ENCODING).toString());

                LogManager.log("Decoding:");
                LogManager.log(audit.countCodingsByDate(CodecUsecase.DECODING).toString());

                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            case "3":
                try {
                    Algorithm encodeCodec = audit.findMostPopularCodec(CodecUsecase.ENCODING);
                    LogManager.log("Encoding:");
                    LogManager.log(encodeCodec.name());

                    Algorithm decodeCodec = audit.findMostPopularCodec(CodecUsecase.DECODING);
                    LogManager.log("Decoding:");
                    LogManager.log(decodeCodec.name());
                } catch (EmptyHistoryException e) {
                    LogManager.log("Cannot perform operation. History is empty");
                }
                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            default:
                throw new OperationUnsupportedException("Invalid input.");
        }
    }


}
