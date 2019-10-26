package org.geekhub.crypto.coders;

import java.util.ArrayList;
import java.util.List;

class CaesarCodec implements Encoder, Decoder {

    private static final int SHIFT_KEY = 15;
    private static final int ALPHABET_COUNT = 26;
    private final List<Character> alphabet;

    public CaesarCodec() {
        alphabet = initializeAlphabet();
    }

    private List<Character> initializeAlphabet() {
        List<Character> result = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            result.add(c);
        }
        return result;
    }

    private char restoreCase(char input, char processedChar) {
        if (Character.isUpperCase(input)) {
            return Character.toUpperCase(processedChar);
        }
        return processedChar;
    }

    private char encodeLetter(char c) {
        int encodedChar = alphabet.indexOf(Character.toLowerCase(c)) + SHIFT_KEY;
        return alphabet.get(encodedChar % ALPHABET_COUNT);
    }

    private char decodeLetter(char c) {
        int decodedChar = alphabet.indexOf(Character.toLowerCase(c)) - SHIFT_KEY;
        return alphabet.get(decodedChar >= 0 ? decodedChar : decodedChar + ALPHABET_COUNT);
    }

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

}
