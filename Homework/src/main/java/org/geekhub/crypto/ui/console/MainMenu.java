package org.geekhub.crypto.ui.console;

import org.geekhub.crypto.coders.*;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.exception.IllegalInputException;
import org.geekhub.crypto.exception.OperationUnsupportedException;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final Logger compositeLogger;
    private final HistoryManager history;


    public MainMenu() {
        scanner = new Scanner(System.in);
        history = new HistoryManager();
        compositeLogger = LoggerFactory.getLogger();
    }

    public void run() {
        while (true) {
            try {
                displayMenu();
            } catch (OperationUnsupportedException e) {
                compositeLogger.warn("Operation is not supported");
            }
        }
    }

    private void displayMenu() {
        System.out.println("Select operation");
        System.out.println("1. Decode \n2. Encode \n3. Analytics \n4. History\n5. Exit");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                Algorithm decoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                System.out.println("Enter text to decode:");
                String textToDecode = scanner.nextLine();
                history.addToHistory(
                        new HistoryRecord(Operation.DECODE, textToDecode, decoderAlgorithm)
                );
                System.out.println("Decoded:");
                try {
                    Decoder decoder = DecodersFactory.getDecoder(decoderAlgorithm);
                    System.out.println(decoder.decode(textToDecode));
                } catch (IllegalArgumentException | IllegalInputException e) {
                    System.out.println("Invalid input, try again");
                } catch (CodecUnsupportedException e) {
                    System.out.println("Cannot create this decoder");
                }
                break;
            case "2":
                Algorithm encoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                System.out.println("Enter text to encode:");
                String textToEncode = scanner.nextLine();
                history.addToHistory(
                        new HistoryRecord(Operation.ENCODE, textToEncode, encoderAlgorithm)
                );

                System.out.println("Encoded:");
                try {
                    Encoder encoder = EncodersFactory.getEncoder(encoderAlgorithm);
                    System.out.println(encoder.encode(textToEncode));
                } catch (IllegalArgumentException | IllegalInputException e) {
                    System.out.println("Invalid input, try again");
                } catch (CodecUnsupportedException e) {
                    System.out.println("Cannot create this encoder");
                }
                break;
            case "3":
                AnalyticsMenu.displayMenu(scanner);
                break;
            case "4":
                HistoryMenu.displayMenu(scanner);
                break;
            case "5":
                scanner.close();
                System.exit(0);
                break;
            default:
                throw new OperationUnsupportedException("Operation is not supported");
        }
    }

}
