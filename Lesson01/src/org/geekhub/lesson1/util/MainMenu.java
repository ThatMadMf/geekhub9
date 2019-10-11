package org.geekhub.lesson1.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import org.geekhub.lesson1.coders.Decoder;
import org.geekhub.lesson1.coders.DecodersFactory;
import org.geekhub.lesson1.coders.Encoder;
import org.geekhub.lesson1.coders.EncodersFactory;

public class MainMenu {

    private Scanner scanner;
    private String[] history;
    private int operationIndex;

    public MainMenu() {
        scanner = new Scanner(System.in);
        history = new String[5];
        operationIndex = 1;
    }

    public void run() {
        while (true) {
            displayMenu();
        }
    }

    private void addToHistory(String operationName, String userInput) {
        if(operationIndex == history.length) {
            history = Arrays.copyOf(history, history.length + 5);
        }
        history[operationIndex - 1] = operationIndex + "." + operationName + " - " + userInput;
        operationIndex++;
    }

    private void displayMenu() {
        System.out.println("Select operation");
        System.out.println("1. Encode \n2. Decode \n3. History \n4. Exit");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                addToHistory("Encode", "1");
                System.out.println("Enter codec method:");
                String encoderAlgorithm = scanner.nextLine();
                addToHistory("Codec name", encoderAlgorithm);

                System.out.println("Enter text to encode:");
                String textToEncode = scanner.nextLine();
                addToHistory("Text to encode", textToEncode);

                Encoder encoder = EncodersFactory.getEncoder(encoderAlgorithm);
                String encoded = encoder.encode(textToEncode);

                System.out.println("Encoded:");
                System.out.println(encoded);
                break;
            case "2":
                addToHistory("Decode", "2");
                System.out.println("Enter codec method:");
                String decoderAlgorithm = scanner.nextLine();
                addToHistory("Codec name", decoderAlgorithm);

                System.out.println("Enter text to decode:");
                String textToDecode = scanner.nextLine();
                addToHistory("Text to decode", textToDecode);


                Decoder decoder = DecodersFactory.getDecoder(decoderAlgorithm);
                String decoded = decoder.decode(textToDecode);

                System.out.println("Decoded:");
                System.out.println(decoded);
                break;
            case "3":
                addToHistory("History", "3");
                for(String record : history) {
                    if(record == null) {
                        break;
                    }
                    System.out.println(record);
                }
                break;
            case "4":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid key. Try again.");
                break;
        }
    }

}
