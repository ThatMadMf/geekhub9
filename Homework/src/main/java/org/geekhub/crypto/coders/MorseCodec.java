package org.geekhub.crypto.coders;

import java.util.Map;

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
        if(input == null) {
            throw new IllegalArgumentException();
        }
        validateCaseOfInput(input);
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();
        int wordCount = 0;

        for (String word : words) {
            result.append(encodeWord(word));
            if (wordCount < words.length - 1) {
                result.append(encodeCharacter(' '));
            }
            wordCount++;
        }
        return result.toString();
    }

    @Override
    public String decode(String input) {
        if(input == null) {
            throw new IllegalArgumentException();
        }
        String[] words = input.split("/(\\.){7}/");
        StringBuilder result = new StringBuilder();

        for (String str : words) {
            result.append(decodeWord(str));
            result.append(" ");
        }

        result.setLength(result.length() - 1);
        return result.toString().toLowerCase();
    }

    private void validateCaseOfInput (String input) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }  else if(Character.isLowerCase(c)) {
                hasLowerCase = true;
            }
            if(hasLowerCase && hasUpperCase) {
                throw new IllegalArgumentException("Text has to be in single case");
            }
        }
    }

    private String encodeCharacter(char c) {
        return CHAR_MAP.get(c) + "/";
    }

    private char decodeCharacter(String string) {
        return CODE_MAP.get(string);
    }

    private String encodeWord(String input) {
        StringBuilder codedWord = new StringBuilder();
        char[] word = input.toCharArray();

        for (char c : word) {
            codedWord.append(encodeCharacter(c));
        }
        return codedWord.toString();
    }

    private String decodeWord(String input) {
        StringBuilder decodedWord = new StringBuilder();
        String[] codedChars = input.split("/");

        for (String codedChar : codedChars) {
            if(!codedChar.equals("")) {
                decodedWord.append(decodeCharacter(codedChar));
            }
        }
        return decodedWord.toString();
    }
}
