package org.geekhub.crypto.ui;

import org.geekhub.crypto.coders.*;

import java.util.Scanner;

public class CipherAlgorithm {

    public String getCodecMethod(Scanner scanner) {
        System.out.println("Enter codec method:");
        int i = 1;
        for (Algorithms algorithm : Algorithms.values()) {
            System.out.println(i + ". " + algorithm);
            i++;
        }
        String input = scanner.nextLine();
        if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= Algorithms.values().length) {
            return Algorithms.values()[Integer.parseInt(input) - 1].toString();
        } else {
            System.out.println("Invalid input");
            throw new IllegalArgumentException();
        }

    }

    public String encode(String name, String text) {
        Encoder encoder = EncodersFactory.getEncoder(name);
        return encoder.encode(text);
    }

    public String decode(String name, String text) {
        Decoder decoder = DecodersFactory.getDecoder(name);
        return decoder.decode(text);
    }
}
