package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.IllegalInputException;

import java.util.List;
import java.util.function.UnaryOperator;

class CaesarCodec implements Encoder, Decoder {

    private static final int SHIFT_KEY = 15;
    private static final List<Character> ALPHABET = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    private static final List<Character> ACCESSIBLE_SYMBOLS = List.of('.', ',', '!', '?', '-', '=', '+', '-', ' ');


    @Override
    public String encode(String input) {
        inputNullCheck(input);

        return input.chars()
                .map(c -> codeWithCase((char) c, CaesarCodec::encodeLetter))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    @Override
    public String decode(String input) {
        inputNullCheck(input);

        return input.chars()
                .map(c -> codeWithCase((char) c, CaesarCodec::decodeLetter))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    private void inputNullCheck(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input have to contain text");
        }
    }

    private char codeWithCase(char input, UnaryOperator<Character> function) {
        if (Character.isLetter(input) && ALPHABET.contains(Character.toLowerCase(input))) {
            if (Character.isLowerCase(input)) {
                return function.apply(input);
            } else {
                return Character.toUpperCase(function.apply(input));
            }
        }
        if (ACCESSIBLE_SYMBOLS.contains(input)) {
            return input;
        }
        throw new IllegalInputException("Unsupported character: " + input);
    }

    private static char encodeLetter(char input) {
        int encodedChar = ALPHABET.indexOf(Character.toLowerCase(input)) + SHIFT_KEY;
        return ALPHABET.get(encodedChar < ALPHABET.size() ? encodedChar : encodedChar - ALPHABET.size());
    }

    private static char decodeLetter(char input) {
        int decodedChar = ALPHABET.indexOf(Character.toLowerCase(input)) - SHIFT_KEY;
        return ALPHABET.get(decodedChar >= 0 ? decodedChar : decodedChar + ALPHABET.size());
    }
}
