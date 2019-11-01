package org.geekhub.crypto.coders;

import java.util.List;

class VigenereCodec implements Encoder, Decoder {
    private static final String SHIFT_KEY = "keyword";
    private static final List<Character> ALPHABET = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    private char encodeLetter(char input, int keywordIndex) {
        if (Character.isLetter(input)) {
            int charIndex = ALPHABET.indexOf(Character.toLowerCase(input));
            int encodedIndex = (charIndex + keywordIndex) % ALPHABET.size();
            return ALPHABET.get(encodedIndex);
        } else {
            return input;
        }
    }

    private char decodeLetter(char input, int keywordIndex) {
        if (Character.isLetter(input)) {
            int charIndex = ALPHABET.indexOf(Character.toLowerCase(input));
            int decodedIndex = (charIndex - keywordIndex + ALPHABET.size()) % ALPHABET.size();
            return ALPHABET.get(decodedIndex);
        } else {
            return input;
        }
    }

    private char restoreCase(char input, char processedChar) {
        if (Character.isUpperCase(input)) {
            return Character.toUpperCase(processedChar);
        }
        return processedChar;
    }

    @Override
    public String encode(String input) {
        StringBuilder result = new StringBuilder();
        int keywordCount = 0;
        for (char symbol : input.toCharArray()) {
            char encodedChar = encodeLetter(symbol, ALPHABET.indexOf(SHIFT_KEY.charAt(keywordCount)));
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
            char decodedChar = decodeLetter(symbol, ALPHABET.indexOf(SHIFT_KEY.charAt(keywordCount)));
            result.append(restoreCase(symbol, decodedChar));
            keywordCount = result.charAt(result.length() - 1) == symbol ? keywordCount : keywordCount + 1;
            keywordCount = keywordCount < SHIFT_KEY.length() ? keywordCount : 0;
        }
        return result.toString();
    }

}
