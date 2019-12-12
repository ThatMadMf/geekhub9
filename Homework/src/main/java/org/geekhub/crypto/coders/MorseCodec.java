package org.geekhub.crypto.coders;

import org.geekhub.crypto.util.IllegalCharacterException;

import java.util.Map;
import java.util.function.UnaryOperator;

import static java.util.Map.entry;

class MorseCodec implements Encoder, Decoder {

    private static final Map<Character, String> CHAR_MAP = Map.ofEntries(
            entry(' ', "......."),
            entry('a', ".-"),
            entry('b', "-..."),
            entry('c', "-.-."),
            entry('d', "-.."),
            entry('e', "."),
            entry('f', "..-."),
            entry('g', "--."),
            entry('h', "...."),
            entry('i', ".."),
            entry('j', ".---"),
            entry('k', "-.-"),
            entry('l', ".-.."),
            entry('m', "--"),
            entry('n', "-."),
            entry('o', "---"),
            entry('p', ".--."),
            entry('q', "--.-"),
            entry('r', ".-."),
            entry('s', "..."),
            entry('t', "-"),
            entry('u', "..-"),
            entry('v', "...-"),
            entry('w', ".--"),
            entry('x', "-..-"),
            entry('y', "-.--"),
            entry('z', "--.."),
            entry('1', ".----"),
            entry('2', "..---"),
            entry('3', "...--"),
            entry('4', "....-"),
            entry('5', "....."),
            entry('6', "-...."),
            entry('7', "--..."),
            entry('8', "---.."),
            entry('9', "----."),
            entry('0', "-----")
    );
    private static final Map<String, Character> CODE_MAP = Map.ofEntries(
            entry(".......", ' '),
            entry(".-", 'a'),
            entry("-...", 'b'),
            entry("-.-.", 'c'),
            entry("-..", 'd'),
            entry(".", 'e'),
            entry("..-.", 'f'),
            entry("--.", 'g'),
            entry("....", 'h'),
            entry("..", 'i'),
            entry(".---", 'j'),
            entry("-.-", 'k'),
            entry(".-..", 'l'),
            entry("--", 'm'),
            entry("-.", 'n'),
            entry("---", 'o'),
            entry(".--.", 'p'),
            entry("--.-", 'q'),
            entry(".-.", 'r'),
            entry("...", 's'),
            entry("-", 't'),
            entry("..-", 'u'),
            entry("...-", 'v'),
            entry(".--", 'w'),
            entry("-..-", 'x'),
            entry("-.--", 'y'),
            entry("--..", 'z'),
            entry(".----", '1'),
            entry("..---", '2'),
            entry("...--", '3'),
            entry("....-", '4'),
            entry(".....", '5'),
            entry("-....", '6'),
            entry("--...", '7'),
            entry("---..", '8'),
            entry("----.", '9'),
            entry("-----", '0')
    );

    @Override
    public String encode(String input) {
        inputCheck(input);
        checkCaseOfInput(input);
        StringBuilder result = new StringBuilder();
        String[] words = input.split("\\s+");

        for (String word : words) {
            result.append(performOperation(word, MorseCodec::encodeWord));
            result.append(CHAR_MAP.get(' '));
            result.append('/');
        }
        return result.toString().substring(0, result.length() - 8);
    }

    @Override
    public String decode(String input) {
        inputCheck(input);
        String result = performOperation(input, MorseCodec::decodeWord);
        return result.toLowerCase();
    }

    private void checkCaseOfInput(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                throw new IllegalArgumentException("Text should be in lower case");
            }
        }
    }

    private void inputCheck(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input have to contain text");
        }
    }

    private static String encodeWord(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            result.append(tryGetCode(c));
            result.append("/");
        }
        return result.toString();
    }

    private static String decodeWord(String input) {
        StringBuilder result = new StringBuilder();
        for (String str : input.split("/")) {
            result.append(tryGetChar(str));
        }
        return result.toString();
    }

    private static String tryGetCode(char c) {
        String code = CHAR_MAP.get(c);
        if (code == null) {
            throw new IllegalCharacterException("Unsupported character: " + c);
        }
        return code;
    }

    private static char tryGetChar(String code) {
        try {
            return CODE_MAP.get(code);
        } catch (NullPointerException e) {
            throw new IllegalCharacterException("Unsupported character: " + code);
        }
    }

    private String performOperation(String text, UnaryOperator<String> function) {
        return function.apply(text);
    }
}
