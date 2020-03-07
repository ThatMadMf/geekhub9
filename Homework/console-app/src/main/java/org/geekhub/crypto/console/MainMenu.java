package org.geekhub.crypto.console;

import org.geekhub.crypto.coders.*;
import org.geekhub.crypto.coders.exception.CodecUnsupportedException;
import org.geekhub.crypto.coders.exception.IllegalInputException;
import org.geekhub.crypto.history.HistoryManager;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.history.Operation;
import org.geekhub.crypto.util.logging.CompositeLogger;
import org.geekhub.crypto.util.logging.Logger;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MainMenu {

    private final Scanner scanner;
    private final Logger logger;
    private final HistoryManager history;
    private final HistoryMenu historyMenu;
    private final AnalyticsMenu analyticsMenu;
    private final DecoderFactory decoderFactory;
    private final EncoderFactory encoderFactory;

    public MainMenu(HistoryManager history, HistoryMenu historyMenu, AnalyticsMenu analyticsMenu,
                    CompositeLogger logger, DecoderFactory decoderFactory, EncoderFactory encoderFactory) {
        scanner = new Scanner(System.in);
        this.logger = logger;
        this.history = history;
        this.historyMenu = historyMenu;
        this.analyticsMenu = analyticsMenu;
        this.decoderFactory = decoderFactory;
        this.encoderFactory = encoderFactory;
    }

    public void run() {
        while (displayMenu()) {
            try {
                displayMenu();
            } catch (OperationUnsupportedException e) {
                logger.warn("Operation is not supported");
            }
        }
    }

    private boolean displayMenu() {
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
                    Decoder decoder = decoderFactory.getDecoder(decoderAlgorithm);
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
                    Encoder encoder = encoderFactory.getEncoder(encoderAlgorithm);
                    System.out.println(encoder.encode(textToEncode));
                } catch (IllegalArgumentException | IllegalInputException e) {
                    System.out.println("Invalid input, try again");
                } catch (CodecUnsupportedException e) {
                    System.out.println("Cannot create this encoder");
                }
                break;
            case "3":
                analyticsMenu.displayMenu(scanner);
                break;
            case "4":
                historyMenu.displayMenu(scanner);
                break;
            case "5":
                scanner.close();
                return false;
            default:
                throw new OperationUnsupportedException("Operation is not supported");
        }
        return true;
    }

}
