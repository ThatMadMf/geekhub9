package org.geekhub.crypto.ui;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;

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
                Algorithm encodeCodec = audit.findMostPopularCodec(CodecUsecase.ENCODING);
                System.out.println("Encoding:");
                System.out.println(encodeCodec == null ? "Empty" : encodeCodec.name());

                Algorithm decodeCodec = audit.findMostPopularCodec(CodecUsecase.DECODING);
                System.out.println("Decoding:");
                System.out.println(decodeCodec == null ? "Empty" : decodeCodec.name());

                history.addToHistory(new HistoryRecord(Operation.ANALYTICS));
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }


}
