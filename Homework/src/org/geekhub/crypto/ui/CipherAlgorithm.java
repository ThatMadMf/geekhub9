package org.geekhub.crypto.ui;

import org.geekhub.crypto.coders.*;

import java.util.Scanner;

class CipherAlgorithm {

    public String getCodecMethod(Scanner scanner) {
        String[] mehthods = {"CAESAR", "MORSE", "VIGENERE", "VIGENERE_2X", "VIGENERE_OVER_CAESAR"};
        int i = 1;
        System.out.println("Enter codec method:");
        for (String method :mehthods) {
            System.out.println(i + ". " + method);
            i++;
        }
        String input = scanner.nextLine();
        if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= mehthods.length) {
            return mehthods[Integer.parseInt(input) - 1];
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
