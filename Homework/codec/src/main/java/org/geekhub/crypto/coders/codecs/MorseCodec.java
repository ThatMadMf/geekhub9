package org.geekhub.crypto.coders.codecs;

import org.geekhub.crypto.coders.*;
import org.geekhub.crypto.coders.exception.IllegalInputException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.UnaryOperator;

@Component
public class MorseCodec implements Encoder, Decoder {

    private static final Map<String, String> CHAR_MAP;
    private static final Map<String, String> CODE_MAP;

    static {
        PropertiesReader reader = new PropertiesReader("coders/MorseDictionary.properties");
        CHAR_MAP = reader.readMap();
        CODE_MAP = MapReverser.reverse(CHAR_MAP);
    }

    @Override
    public String encode(String input) {
        inputNullCheck(input);
        checkCaseOfInput(input);
        StringBuilder result = new StringBuilder();
        String[] words = input.split("\\s+");

        for (String word : words) {
            result.append(performOperation(word, MorseCodec::encodeWord));
            result.append(CHAR_MAP.get(" "));
            result.append('/');
        }
        return result.toString().substring(0, result.length() - 8);
    }

    @Override
    public String decode(String input) {
        inputNullCheck(input);
        String result = performOperation(input, MorseCodec::decodeWord);
        return result.toLowerCase();
    }

    @Override
    public Algorithm getAlgorithm() {
        return Algorithm.MORSE;
    }

    private void checkCaseOfInput(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                throw new IllegalArgumentException("Text should be in lower case");
            }
        }
    }

    private void inputNullCheck(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input have to contain text");
        }
    }

    private static String encodeWord(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            result.append(getCode(String.valueOf(c)));
            result.append("/");
        }
        return result.toString();
    }

    private static String decodeWord(String input) {
        StringBuilder result = new StringBuilder();
        for (String str : input.split("/")) {
            result.append(getChar(str));
        }
        return result.toString();
    }

    private static String getCode(String c) {
        String code = CHAR_MAP.get(c);
        if (code == null) {
            throw new IllegalInputException("Unsupported character: " + c);
        }
        return code;
    }

    private static String getChar(String code) {
        String symbol = CODE_MAP.get(code);
        if (symbol == null) {
            throw new IllegalInputException("Unsupported character: " + code);
        }
        return symbol;
    }

    private String performOperation(String text, UnaryOperator<String> function) {
        return function.apply(text);
    }
}
