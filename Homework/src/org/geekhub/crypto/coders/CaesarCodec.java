package org.geekhub.crypto.coders;

import java.util.List;

class CaesarCodec implements Encoder, Decoder {

    private static final int SHIFT_KEY = 15;
    private static final List<Character> ALPHABET = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    @Override
    public String encode(String input) {
        StringBuilder result = new StringBuilder();
        for (char symbol : input.toCharArray()) {
            if (Character.isLetter(symbol)) {
                result.append(restoreCase(symbol, encodeLetter(symbol)));
            } else {
                result.append(symbol);
            }
        }
        return result.toString();
    }

    @Override
    public String decode(String input) {
        StringBuilder result = new StringBuilder();
        for (char symbol : input.toCharArray()) {
            if (Character.isLetter(symbol)) {
                result.append(restoreCase(symbol, decodeLetter(symbol)));
            } else {
                result.append(symbol);
            }
        }
        return result.toString();
    }

    private char restoreCase(char input, char processedChar) {
        if (Character.isUpperCase(input)) {
            return Character.toUpperCase(processedChar);
        }
        return processedChar;
    }

    private char encodeLetter(char c) {
        int encodedChar = ALPHABET.indexOf(Character.toLowerCase(c)) + SHIFT_KEY;
        return ALPHABET.get(encodedChar % ALPHABET.size());
    }

    private char decodeLetter(char c) {
        int decodedChar = ALPHABET.indexOf(Character.toLowerCase(c)) - SHIFT_KEY;
        return ALPHABET.get(decodedChar >= 0 ? decodedChar : decodedChar + ALPHABET.size());
    }


}
