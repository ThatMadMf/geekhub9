package org.geekhub.crypto.ui;

import java.util.Scanner;

public class MainMenu {

    private Scanner scanner;
    private History history;

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
                CipherAlgorithm encoder = new CipherAlgorithm();
                history.addToHistory("Encode", "1");
                String encoderAlgorithm = encoder.getCodecMethod(scanner);
                history.addToHistory("Codec name", encoderAlgorithm);

                System.out.println("Enter text to encode:");
                String textToEncode = scanner.nextLine();
                history.addToHistory("Text to encode", textToEncode);

                System.out.println("Encoded:");
                System.out.println(encoder.encode(encoderAlgorithm, textToEncode));
                break;
            case "2":
                CipherAlgorithm decoder = new CipherAlgorithm();
                history.addToHistory("Decode", "2");
                String decoderAlgorithm = decoder.getCodecMethod(scanner);
                history.addToHistory("Codec name", decoderAlgorithm);

                System.out.println("Enter text to decode:");
                String textToDecode = scanner.nextLine();
                history.addToHistory("Text to decode", textToDecode);

                System.out.println("Decoded:");
                System.out.println(decoder.decode(decoderAlgorithm, textToDecode));
                break;
            case "3":
                history.addToHistory("History", "3");
                history.showHistory();
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
