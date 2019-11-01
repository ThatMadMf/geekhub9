package org.geekhub.crypto.ui;

import java.util.Scanner;

class CodecNameReader {

    static String getCodecMethod(Scanner scanner) {
        String[] mehthods = {"CAESAR", "MORSE", "VIGENERE", "VIGENERE_2X", "VIGENERE_OVER_CAESAR"};
        int i = 1;
        System.out.println("Enter codec method:");
        for (String method : mehthods) {
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
}
