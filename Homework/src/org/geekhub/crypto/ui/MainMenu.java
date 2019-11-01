package org.geekhub.crypto.ui;

import org.geekhub.crypto.coders.Decoder;
import org.geekhub.crypto.coders.DecodersFactory;
import org.geekhub.crypto.coders.Encoder;
import org.geekhub.crypto.coders.EncodersFactory;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final History history;

    public MainMenu() {
        scanner = new Scanner(System.in);
        history = new History();
    }

    public void run() {
        while (true) {
            displayMenu();
        }
    }

    private void displayMenu() {
        System.out.println("Select operation");
        System.out.println("1. Encode \n2. Decode \n3. History \n4. Exit");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                history.addToHistory("Encode", "1");
                String encoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                history.addToHistory("Codec name", encoderAlgorithm);

                System.out.println("Enter text to encode:");
                String textToEncode = scanner.nextLine();
                history.addToHistory("Text to encode", textToEncode);

                System.out.println("Encoded:");
                Encoder encoder = EncodersFactory.getEncoder(encoderAlgorithm);
                System.out.println(encoder.encode(textToEncode));
                break;
            case "2":
                history.addToHistory("Decode", "2");
                String decoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                history.addToHistory("Codec name", decoderAlgorithm);

                System.out.println("Enter text to decode:");
                String textToDecode = scanner.nextLine();
                history.addToHistory("Text to decode", textToDecode);

                System.out.println("Decoded:");
                Decoder decoder = DecodersFactory.getDecoder(decoderAlgorithm);
                System.out.println(decoder.decode(textToDecode));
                break;
            case "3":
                HistoryMenu.displayMenu(scanner, history);
                break;
            case "4":
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input. Try again.");
                break;
        }
    }

}
