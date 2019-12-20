package org.geekhub.crypto.ui;

import org.geekhub.crypto.coders.*;
import org.geekhub.crypto.exception.IllegalInputException;
import org.geekhub.crypto.exception.OperationUnsupportedException;
import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;

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
            try {
                displayMenu();
            } catch (OperationUnsupportedException e) {
                LogManager.warn("Operation is not supported");
            }
        }
    }

    private void displayMenu() {
        Logger compositeLogger = LoggerFactory.getLogger();
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
                Decoder decoder = DecodersFactory.getDecoder(decoderAlgorithm);
                try {
                    System.out.println(decoder.decode(textToDecode));
                } catch (IllegalArgumentException | IllegalInputException e) {
                    compositeLogger.warn(LogManager.INVALID_INPUT);
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
                Encoder encoder = EncodersFactory.getEncoder(encoderAlgorithm);
                try {
                    System.out.println(encoder.encode(textToEncode));
                } catch (IllegalArgumentException | IllegalInputException e) {
                    compositeLogger.warn(LogManager.INVALID_INPUT);
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
