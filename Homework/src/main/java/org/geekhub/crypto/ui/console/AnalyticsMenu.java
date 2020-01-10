package org.geekhub.crypto.ui.console;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.exception.EmptyHistoryException;
import org.geekhub.crypto.exception.OperationUnsupportedException;

import java.util.Scanner;

class AnalyticsMenu {
    public static void displayMenu(Scanner scanner, CodingHistory history) {
        System.out.println("1 - Count inputs\n2 - Count by date\n3 - The most popular algorithm");
        String input = scanner.nextLine();
        CodingAudit audit = new CodingAudit(history);

        switch (input) {
            case "1":
                System.out.println(audit.countEncodingInputs().toString());
                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            case "2":
                System.out.println("Encoding;");
                System.out.println(audit.countCodingsByDate(CodecUsecase.ENCODING).toString());

                System.out.println("Decoding:");
                System.out.println(audit.countCodingsByDate(CodecUsecase.DECODING).toString());

                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            case "3":
                System.out.println("Encoding:");
                try {
                    Algorithm encodeCodec = audit.findMostPopularCodec(CodecUsecase.ENCODING);
                    System.out.println(encodeCodec.name());
                } catch (EmptyHistoryException e) {
                    System.out.println("History is empty");
                }
                System.out.println("Decoding:");
                try {
                    Algorithm decodeCodec = audit.findMostPopularCodec(CodecUsecase.DECODING);
                    System.out.println(decodeCodec.name());
                } catch (EmptyHistoryException e) {
                    System.out.println("History is empty");
                }
                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            default:
                throw new OperationUnsupportedException("Invalid input.");
        }
    }


}
