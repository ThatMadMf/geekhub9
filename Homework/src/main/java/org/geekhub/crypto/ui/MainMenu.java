package main.java.org.geekhub.crypto.ui;

import main.java.org.geekhub.crypto.coders.*;
import main.java.org.geekhub.crypto.history.Operation;
import main.java.org.geekhub.crypto.history.CodingHistory;

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
        System.out.println("1. Decode \n2. Encode \n3. Analytics \n4. History\n5. Exit");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                String decoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                System.out.println("Enter text to decode:");
                String textToDecode = scanner.nextLine();
                history.addToHistory(Operation.DECODE, textToDecode, Algorithm.valueOf(decoderAlgorithm));

                System.out.println("Decoded:");
                Decoder decoder = DecodersFactory.getDecoder(decoderAlgorithm);
                System.out.println(decoder.decode(textToDecode));
                break;
            case "2":
                String encoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                System.out.println("Enter text to encode:");
                String textToEncode = scanner.nextLine();
                history.addToHistory(Operation.ENCODE, textToEncode, Algorithm.valueOf(encoderAlgorithm));

                System.out.println("Encoded:");
                Encoder encoder = EncodersFactory.getEncoder(encoderAlgorithm);
                System.out.println(encoder.encode(textToEncode));
                break;
            case "3":
                AnalyticsMenu.displayMenu(scanner, history);
                break;
            case "4":
                HistoryMenu.displayMenu(scanner, history);
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
