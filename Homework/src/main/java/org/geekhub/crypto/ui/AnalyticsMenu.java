package org.geekhub.crypto.ui;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.util.EmptyHistoryException;
import org.geekhub.crypto.util.OperationUnsupportedException;

import java.util.Scanner;

class AnalyticsMenu {
    public static void displayMenu(Scanner scanner, CodingHistory history) {
        System.out.println("1 - Count inputs\n2 - Count by date\n3 - The most popular algorithm");
        String input = scanner.nextLine();
        CodingAudit audit = new CodingAudit(history);

        switch (input) {
            case "1":
                System.out.println(audit.countEncodingInputs());
                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            case "2":
                System.out.println("Encoding;");
                System.out.println(audit.countCodingsByDate(CodecUsecase.ENCODING));

                System.out.println("Decoding:");
                System.out.println(audit.countCodingsByDate(CodecUsecase.DECODING));

                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            case "3":
                try {
                    Algorithm encodeCodec = audit.findMostPopularCodec(CodecUsecase.ENCODING);
                    System.out.println("Encoding:");
                    System.out.println(encodeCodec.name());

                    Algorithm decodeCodec = audit.findMostPopularCodec(CodecUsecase.DECODING);
                    System.out.println("Decoding:");
                    System.out.println(decodeCodec.name());
                } catch (EmptyHistoryException e) {
                    System.out.println("Cannot perform operation. History is empty");
                }
                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            default:
                throw new OperationUnsupportedException("Invalid input.");
        }
    }


}
