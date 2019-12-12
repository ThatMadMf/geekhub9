package org.geekhub.crypto.ui;

import org.geekhub.crypto.coders.*;
import org.geekhub.crypto.history.Operation;

import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.util.ConsoleLogger;
import org.geekhub.crypto.util.IllegalCharacterException;
import org.geekhub.crypto.util.Logger;
import org.geekhub.crypto.util.OperationUnsupportedException;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final CodingHistory history;
    private final Logger consoleLogger;

    public MainMenu() {
        scanner = new Scanner(System.in);
        history = new CodingHistory();
        consoleLogger = new ConsoleLogger();
    }

    public void run() {
        while (true) {
            try {
                displayMenu();
            } catch (OperationUnsupportedException e) {
                consoleLogger.warn("Operation is not supported");
            }
        }
    }

    private void displayMenu() {
        consoleLogger.log("Select operation");
        consoleLogger.log("1. Decode \n2. Encode \n3. Analytics \n4. History\n5. Exit");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                String decoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                consoleLogger.log("Enter text to decode:");
                String textToDecode = scanner.nextLine();
                history.addToHistory(
                        new HistoryRecord(Operation.DECODE, textToDecode, Algorithm.valueOf(decoderAlgorithm))
                );

                consoleLogger.log("Decoded:");
                Decoder decoder = DecodersFactory.getDecoder(decoderAlgorithm);
                try {
                    consoleLogger.log(decoder.decode(textToDecode));
                } catch (IllegalArgumentException | IllegalCharacterException e) {
                    consoleLogger.warn("Invalid input, try again");
                }
                break;
            case "2":
                String encoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                consoleLogger.log("Enter text to encode:");
                String textToEncode = scanner.nextLine();
                history.addToHistory(
                        new HistoryRecord(Operation.ENCODE, textToEncode, Algorithm.valueOf(encoderAlgorithm))
                );

                consoleLogger.log("Encoded:");
                Encoder encoder = EncodersFactory.getEncoder(encoderAlgorithm);
                try {
                    consoleLogger.log(encoder.encode(textToEncode));
                } catch (IllegalArgumentException | IllegalCharacterException e) {
                    consoleLogger.warn("Invalid input, try again");
                }
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
                throw new OperationUnsupportedException("Operation is not supported");
        }
    }

}
