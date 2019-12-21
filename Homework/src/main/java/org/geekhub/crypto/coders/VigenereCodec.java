package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.IllegalInputException;

import java.util.List;
import java.util.function.BiFunction;

@Codec(algorithm = Algorithm.VIGENERE)
class VigenereCodec implements Encoder, Decoder {
    private static final String SHIFT_KEY = "keyword";
    private static final List<Character> ALPHABET = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    private static final List<Character> ACCESSIBLE_SYMBOLS = List.of('.', ',', '!', '?', '-', '=', '+', '-', ' ');

    @Override
    public String encode(String input) {
        checkInput(input);
        StringBuilder result = new StringBuilder();

        int keywordCount = 0;
        for (char symbol : input.toCharArray()) {
            result.append(codeWithCase(symbol, getIndex(keywordCount), VigenereCodec::encodeLetter));
            keywordCount = getNextKeyIndex(keywordCount, result, symbol);
        }
        return result.toString();
    }

    @Override
    public String decode(String input) {
        checkInput(input);
        StringBuilder result = new StringBuilder();
        int keywordCount = 0;
        for (char symbol : input.toCharArray()) {
            result.append(codeWithCase(symbol, getIndex(keywordCount), VigenereCodec::decodeLetter));
            keywordCount = getNextKeyIndex(keywordCount, result, symbol);
        }
        return result.toString();
    }

    private void checkInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input have to contain text");
        }
    }

    private int getIndex(int keyIndex) {
        return ALPHABET.indexOf(SHIFT_KEY.charAt(keyIndex));
    }

    private int getNextKeyIndex(int keywordCount, StringBuilder result, char lastChar) {
        keywordCount = result.charAt(result.length() - 1) == lastChar ? keywordCount : keywordCount + 1;
        keywordCount = keywordCount < SHIFT_KEY.length() ? keywordCount : 0;
        return keywordCount;
    }

    private static char encodeLetter(char input, int keywordIndex) {
        if (Character.isLetter(input) && ALPHABET.contains(Character.toLowerCase(input))) {
            int charIndex = ALPHABET.indexOf(Character.toLowerCase(input));
            int encodedIndex = (charIndex + keywordIndex) % ALPHABET.size();
            return ALPHABET.get(encodedIndex);
        }
        if (ACCESSIBLE_SYMBOLS.contains(input)) {
            return input;
        }
        throw new IllegalInputException("Unsupported character: " + input);
    }

    private static char decodeLetter(char input, int keywordIndex) {
        if (Character.isLetter(input) && ALPHABET.contains(Character.toLowerCase(input))) {
            int charIndex = ALPHABET.indexOf(Character.toLowerCase(input));
            int decodedIndex = (charIndex - keywordIndex + ALPHABET.size()) % ALPHABET.size();
            return ALPHABET.get(decodedIndex);
        }
        if (ACCESSIBLE_SYMBOLS.contains(input)) {
            return input;
        }
        throw new IllegalInputException("Unsupported character: " + input);
    }

    private char codeWithCase(char input, int key, BiFunction<Character, Integer, Character> function) {
        if (Character.isUpperCase(input)) {
            return Character.toUpperCase(function.apply(input, key));
        }
        return function.apply(input, key);
    }
}
