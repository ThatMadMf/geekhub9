package org.geekhub.crypto.ui;

import org.geekhub.crypto.coders.*;
import org.geekhub.crypto.history.Operation;

import org.geekhub.crypto.history.CodingHistory;
import org.geekhub.crypto.history.HistoryRecord;
import org.geekhub.crypto.util.*;

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
        LogManager.log("Select operation");
        LogManager.log("1. Decode \n2. Encode \n3. Analytics \n4. History\n5. Exit");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                String decoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                LogManager.log("Enter text to decode:");
                String textToDecode = scanner.nextLine();
                history.addToHistory(
                        new HistoryRecord(Operation.DECODE, textToDecode, Algorithm.valueOf(decoderAlgorithm))
                );

                LogManager.log("Decoded:");
                Decoder decoder = DecodersFactory.getDecoder(decoderAlgorithm);
                try {
                    LogManager.log(decoder.decode(textToDecode));
                } catch (IllegalArgumentException | IllegalCharacterException e) {
                    LogManager.warn(LogManager.INVALID_INPUT);
                }
                break;
            case "2":
                String encoderAlgorithm = CodecNameReader.getCodecMethod(scanner);
                LogManager.log("Enter text to encode:");
                String textToEncode = scanner.nextLine();
                history.addToHistory(
                        new HistoryRecord(Operation.ENCODE, textToEncode, Algorithm.valueOf(encoderAlgorithm))
                );

                LogManager.log("Encoded:");
                Encoder encoder = EncodersFactory.getEncoder(encoderAlgorithm);
                try {
                    LogManager.log(encoder.encode(textToEncode));
                } catch (IllegalArgumentException | IllegalCharacterException e) {
                    LogManager.warn(LogManager.INVALID_INPUT);
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
                LogManager.close();
                System.exit(0);
                break;
            default:
                throw new OperationUnsupportedException("Operation is not supported");
        }
    }

}
