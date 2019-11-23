package org.geekhub.crypto.coders;

import java.util.List;
import java.util.function.Function;

class CaesarCodec implements Encoder, Decoder {

    private static final int SHIFT_KEY = 15;
    private static final List<Character> ALPHABET = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    @Override
    public String encode(String input) {
        nullCheck(input);
        StringBuilder result = new StringBuilder();

        input.chars().forEachOrdered(c -> {
            if (Character.isLetter(c)) {
                result.append(codeWithCase((char) c, encodeLetter));
            } else {
                result.append((char) c);
            }
        });
        return result.toString();
    }

    @Override
    public String decode(String input) {
        nullCheck(input);
        StringBuilder result = new StringBuilder();

        input.chars().forEachOrdered(c -> {
            if (Character.isLetter(c)) {
                result.append(codeWithCase((char) c, decodeLetter));
            } else {
                result.append((char) c);
            }
        });
        return result.toString();
    }

    private void nullCheck(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
    }

    private char codeWithCase(char input, Function<Character, Character> function) {
        if (Character.isUpperCase(input)) {
            return Character.toUpperCase(function.apply(input));
        }
        return function.apply(input);
    }

    private Function<Character, Character> encodeLetter = c -> {
        int encodedChar = ALPHABET.indexOf(Character.toLowerCase(c)) + SHIFT_KEY;
        return ALPHABET.get(encodedChar % ALPHABET.size());
    };

    private Function<Character, Character> decodeLetter = c -> {
        int decodedChar = ALPHABET.indexOf(Character.toLowerCase(c)) - SHIFT_KEY;
        return ALPHABET.get(decodedChar >= 0 ? decodedChar : decodedChar + ALPHABET.size());
    };
}
