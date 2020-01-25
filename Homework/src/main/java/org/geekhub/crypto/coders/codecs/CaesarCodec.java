package org.geekhub.crypto.coders.codecs;

import org.geekhub.crypto.coders.Decoder;
import org.geekhub.crypto.coders.Encoder;
import org.geekhub.crypto.exception.IllegalInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.UnaryOperator;

@Component("CAESAR")
public class CaesarCodec implements Encoder, Decoder {

    private static final List<Character> ALPHABET = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    private static final List<Character> ACCESSIBLE_SYMBOLS = List.of('.', ',', '!', '?', '-', '=', '+', '-', ' ');

    private final int shift;

    public CaesarCodec(@Value("${shift}") int shift) {
        this.shift = shift;
    }

    @Override
    public String encode(String input) {
        inputNullCheck(input);

        return input.chars()
                .map(c -> codeWithCase((char) c, this::encodeLetter))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    @Override
    public String decode(String input) {
        inputNullCheck(input);

        return input.chars()
                .map(c -> codeWithCase((char) c, this::decodeLetter))
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

    private char encodeLetter(char input) {
        int encodedChar = ALPHABET.indexOf(Character.toLowerCase(input)) + this.shift;
        return ALPHABET.get(encodedChar < ALPHABET.size() ? encodedChar : encodedChar - ALPHABET.size());
    }

    private char decodeLetter(char input) {
        int decodedChar = ALPHABET.indexOf(Character.toLowerCase(input)) - shift;
        return ALPHABET.get(decodedChar >= 0 ? decodedChar : decodedChar + ALPHABET.size());
    }
}
