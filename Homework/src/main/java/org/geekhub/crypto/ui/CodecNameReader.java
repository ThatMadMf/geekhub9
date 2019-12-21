package org.geekhub.crypto.ui;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.exception.OperationUnsupportedException;

import java.util.Scanner;

class CodecNameReader {

    static Algorithm getCodecMethod(Scanner scanner) {
        int i = 1;
        System.out.println("Enter codec method:");
        for (Algorithm method : Algorithm.values()) {
            System.out.println(i + ". " + method);
            i++;
        }
        String input = scanner.nextLine();
        if (Integer.parseInt(input) >= 1 && Integer.parseInt(input) <= Algorithm.values().length) {
            return Algorithm.values()[Integer.parseInt(input) - 1];
        } else {
            throw new OperationUnsupportedException("Operation is not supported");
        }

    }
}
