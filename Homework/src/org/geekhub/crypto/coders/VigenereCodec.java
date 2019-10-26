package org.geekhub.crypto.coders;

import java.util.ArrayList;
import java.util.List;

class VigenereCodec implements Encoder, Decoder {
    private static final String SHIFT_KEY = "keyword";
    private static final int ALPHABET_COUNT = 26;
    private final List<Character> alphabet;

    public VigenereCodec() {
        alphabet = initializeAlphabet();
    }

    private List<Character> initializeAlphabet() {
        List<Character> result = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            result.add(c);
        }
        return result;
    }

    private char encodeLetter(char input, int keywordIndex) {
        if (Character.isLetter(input)) {
            int charIndex = alphabet.indexOf(Character.toLowerCase(input));
            int encodedIndex = (charIndex + keywordIndex) % ALPHABET_COUNT;
            return alphabet.get(encodedIndex);
        } else {
            return input;
        }
    }

    private char decodeLetter(char input, int keywordIndex) {
        if (Character.isLetter(input)) {
            int charIndex = alphabet.indexOf(Character.toLowerCase(input));
            int decodedIndex = (charIndex - keywordIndex + ALPHABET_COUNT) % ALPHABET_COUNT;
            return alphabet.get(decodedIndex);
        } else {
            return input;
        }
    }

    private char restoreCase(char input, char processedChar) {
        if(Character.isUpperCase(input)) {
            return Character.toUpperCase(processedChar);
        }
        return processedChar;
    }

    @Override
    public String encode(String input) {
        StringBuilder result = new StringBuilder();
        int keywordCount = 0;
        for (char symbol : input.toCharArray()) {
            char encodedChar = encodeLetter(symbol, alphabet.indexOf(SHIFT_KEY.charAt(keywordCount)));
            result.append(restoreCase(symbol, encodedChar));
            keywordCount = result.charAt(result.length() - 1) == symbol ? keywordCount : keywordCount + 1;
            keywordCount = keywordCount < SHIFT_KEY.length() ? keywordCount : 0;
        }
        return result.toString();
    }

    @Override
    public String decode(String input) {
        StringBuilder result = new StringBuilder();
        int keywordCount = 0;
        for (char symbol : input.toCharArray()) {
            char decodedChar = decodeLetter(symbol, alphabet.indexOf(SHIFT_KEY.charAt(keywordCount)));
            result.append(restoreCase(symbol, decodedChar));
            keywordCount = result.charAt(result.length() - 1) == symbol ? keywordCount : keywordCount + 1;
            keywordCount = keywordCount < SHIFT_KEY.length() ? keywordCount : 0;
        }
        return result.toString();
    }

}
