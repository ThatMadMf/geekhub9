package org.geekhub.crypto.ui;

import org.geekhub.crypto.coders.Algorithms;

import java.util.Scanner;

class CodecNameReader {

    static String getCodecMethod(Scanner scanner) {
        int i = 1;
        System.out.println("Enter codec method:");
        for (Algorithms method : Algorithms.values()) {
            System.out.println(i + ". " + method);
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
}
