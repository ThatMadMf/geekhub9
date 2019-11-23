package org.geekhub.crypto.coders;

import java.util.List;
import java.util.function.BiFunction;

class VigenereCodec implements Encoder, Decoder {
    private static final String SHIFT_KEY = "keyword";
    private static final List<Character> ALPHABET = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    @Override
    public String encode(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder result = new StringBuilder();

        int keywordCount = 0;
        for (char symbol : input.toCharArray()) {
            result.append(codeWithCase(symbol, getIndex(keywordCount), encodeLetter));
            keywordCount = getNextKeyIndex(keywordCount, result, symbol);
        }
        return result.toString();
    }

    @Override
    public String decode(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder result = new StringBuilder();
        int keywordCount = 0;
        for (char symbol : input.toCharArray()) {
            result.append(codeWithCase(symbol, getIndex(keywordCount), decodeLetter));
            keywordCount = getNextKeyIndex(keywordCount, result, symbol);
        }
        return result.toString();
    }

    private int getIndex(int keyIndex) {
        return ALPHABET.indexOf(SHIFT_KEY.charAt(keyIndex));
    }

    private int getNextKeyIndex(int keywordCount, StringBuilder result, char lastChar) {
        keywordCount = result.charAt(result.length() - 1) == lastChar ? keywordCount : keywordCount + 1;
        keywordCount = keywordCount < SHIFT_KEY.length() ? keywordCount : 0;
        return keywordCount;
    }

    private BiFunction<Character, Integer, Character> encodeLetter = (input, keywordIndex) -> {
        if (Character.isLetter(input)) {
            int charIndex = ALPHABET.indexOf(Character.toLowerCase(input));
            int encodedIndex = (charIndex + keywordIndex) % ALPHABET.size();
            return ALPHABET.get(encodedIndex);
        } else {
            return input;
        }
    };

    private BiFunction<Character, Integer, Character> decodeLetter = (input, keywordIndex) -> {
        if (Character.isLetter(input)) {
            int charIndex = ALPHABET.indexOf(Character.toLowerCase(input));
            int decodedIndex = (charIndex - keywordIndex + ALPHABET.size()) % ALPHABET.size();
            return ALPHABET.get(decodedIndex);
        } else {
            return input;
        }
    };

    private char codeWithCase(char input, int key, BiFunction<Character, Integer, Character> function) {
        if (Character.isUpperCase(input)) {
            return Character.toUpperCase(function.apply(input, key));
        }
        return function.apply(input, key);
    }
}
