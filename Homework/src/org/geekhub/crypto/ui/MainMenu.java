package org.geekhub.crypto.ui;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.coders.*;
import org.geekhub.crypto.history.CodingHistory;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final CodingHistory history;

    public MainMenu() {
        scanner = new Scanner(System.in);
        history = new CodingHistory();
    }

    public void run() {
        while (true) {
            displayMenu();
        }
    }

    private void displayMenu() {
        System.out.println("Select operation");
        System.out.println("1. Encode \n2. Decode \n3. History \n4. Analytics\n5. Exit");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                String encoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                System.out.println("Enter text to encode:");
                String textToEncode = scanner.nextLine();
                history.addToHistory(CodecUsecase.ENCODING, textToEncode, Algorithm.valueOf(encoderAlgorithm));

                System.out.println("Encoded:");
                Encoder encoder = EncodersFactory.getEncoder(encoderAlgorithm);
                System.out.println(encoder.encode(textToEncode));
                break;
            case "2":
                String decoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                System.out.println("Enter text to decode:");
                String textToDecode = scanner.nextLine();
                history.addToHistory(CodecUsecase.DECODING, textToDecode, Algorithm.valueOf(decoderAlgorithm));

                System.out.println("Decoded:");
                Decoder decoder = DecodersFactory.getDecoder(decoderAlgorithm);
                System.out.println(decoder.decode(textToDecode));
                break;
            case "3":
                HistoryMenu.displayMenu(scanner, history);
                break;
            case "4":
                AnalyticsMenu.displayMenu(scanner, history);
                break;
            case "5":
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input. Try again.");
                break;
        }
    }

}
