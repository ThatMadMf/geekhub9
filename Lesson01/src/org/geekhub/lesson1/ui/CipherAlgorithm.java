package org.geekhub.lesson1.ui;

import org.geekhub.lesson1.coders.Decoder;
import org.geekhub.lesson1.coders.DecodersFactory;
import org.geekhub.lesson1.coders.Encoder;
import org.geekhub.lesson1.coders.EncodersFactory;

import java.util.Scanner;

public class CipherAlgorithm {

    public String getCodecMethod(Scanner scanner) {
        System.out.println("Enter codec method(CAESAR, MORSE, VIGENERE):");
        return scanner.nextLine().toUpperCase();
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
