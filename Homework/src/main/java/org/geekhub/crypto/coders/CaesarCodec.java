package org.geekhub.crypto.coders;

import java.util.List;
import java.util.function.UnaryOperator;

class CaesarCodec implements Encoder, Decoder {

    private static final int SHIFT_KEY = 15;
    private static final List<Character> ALPHABET = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');


    @Override
    public String encode(String input) {
        nullCheck(input);
        StringBuilder result = new StringBuilder();

        input.chars().forEachOrdered(c -> {
            if (Character.isLetter(c)) {
                result.append(codeWithCase((char) c, symbol ->
                        ALPHABET.get((getIndex(symbol) + SHIFT_KEY) % ALPHABET.size())));
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
                result.append(codeWithCase((char) c, CaesarCodec::decodeLetter));
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

    private char codeWithCase(char input, UnaryOperator<Character> function) {
        if (Character.isUpperCase(input)) {
            return Character.toUpperCase(function.apply(input));
        }
        return function.apply(input);
    }

    private static char decodeLetter(char input) {
        int decodedChar = ALPHABET.indexOf(Character.toLowerCase(input)) - SHIFT_KEY;
        return ALPHABET.get(decodedChar >= 0 ? decodedChar : decodedChar + ALPHABET.size());
    }

    private int getIndex(char c) {
        return ALPHABET.indexOf(Character.toLowerCase(c));
    }
}
