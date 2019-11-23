package org.geekhub.crypto.coders;

import java.util.Arrays;
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
        nullCheck(input);
        checkCaseOfInput(input);
        StringBuilder result = new StringBuilder();

        Arrays.stream(input.split("\\s+")).forEach(word -> {
            result.append(performOperation(word, MorseCodec::encodeWord));
            result.append(CHAR_MAP.get(' '));
            result.append('/');
        });
        return trimIfNotEmpty(result).toString();
    }

    @Override
    public String decode(String input) {
        nullCheck(input);
        if (input.isEmpty()) {
            return "";
        }
        String result = performOperation(input, MorseCodec::decodeWord);
        return result.toLowerCase();
    }

    private void checkCaseOfInput(String input) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
            if (hasLowerCase && hasUpperCase) {
                throw new IllegalArgumentException("Text has to be in single case");
            }
        }
    }

    private void nullCheck(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
    }

    private StringBuilder trimIfNotEmpty(StringBuilder input) {
        if (input.length() > 0) {
            input.setLength(input.length() - 8);
        }
        return input;
    }

    private static String encodeWord(String input) {
        StringBuilder result = new StringBuilder();
        input.chars().forEachOrdered(c -> result.append(CHAR_MAP.get((char) c)).append('/'));

        return result.toString();
    }

    private static String decodeWord(String input) {
        StringBuilder result = new StringBuilder();
        Arrays.stream(input.split("/")).forEach(w -> result.append(CODE_MAP.get(w)));

        return result.toString();
    }

    private String performOperation(String text, UnaryOperator<String> function) {
        return function.apply(text);
    }
}
