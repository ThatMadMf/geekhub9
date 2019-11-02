package org.geekhub.crypto.ui;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.analytics.CodingAudit;
import org.geekhub.crypto.history.CodingHistory;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class AnalyticsMenu {
    public static void displayMenu(Scanner scanner, CodingHistory history) {
        System.out.println("1 - Count inputs\n2 - Count by date\n3 - The most popular algorithm");
        String input = scanner.nextLine();
        CodingAudit audit = new CodingAudit(history);

        switch (input) {
            case "1":
                System.out.println(audit.countEncodingInputs());
                break;
            case "2":
                System.out.println("Encoding;");
                System.out.println(audit.countCodingsByDate(CodecUsecase.ENCODING));
                System.out.println("Decoding:");
                System.out.println(audit.countCodingsByDate(CodecUsecase.DECODING));
                break;
            case "3":
                System.out.println("Encoding:");
                System.out.println(audit.findMostPopularCodec(CodecUsecase.ENCODING).toString());
                System.out.println("Decoding:");
                System.out.println(audit.findMostPopularCodec(CodecUsecase.DECODING).toString());
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }


}
