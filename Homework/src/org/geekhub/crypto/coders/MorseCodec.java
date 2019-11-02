package org.geekhub.crypto.coders;

import java.util.HashMap;
import java.util.Map;

class MorseCodec implements Encoder, Decoder {

    private static final char[] ALPHABET = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '0'};
    private static final String[] CODES = new String[]{".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
            ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
            "-.--", "--..", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----"};

    private static Map<Character, String> charMap;
    private static Map<String, Character> codeMap;

    static {
        charMap = new HashMap<>();
        codeMap = new HashMap<>();
        for (int i = 0; i < ALPHABET.length; i++) {
            charMap.put(ALPHABET[i], CODES[i]);
            codeMap.put(CODES[i], ALPHABET[i]);
        }
    }

    @Override
    public String encode(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                System.out.println("Error. You have to use characters of single case.");
                System.exit(1);
            }
        }

        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();
        int wordCount = 0;

        for (String word : words) {
            result.append(encodeWord(word));
            if (wordCount < words.length - 1) {
                result.append("......./");
            }
            wordCount++;
        }
        return result.toString();
    }

    @Override
    public String decode(String input) {
        String[] words = input.split("/(\\.){7}/");
        StringBuilder result = new StringBuilder();

        for (String str : words) {
            result.append(decodeWord(str));
            result.append(" ");
        }

        result.setLength(result.length() - 1);
        return result.toString().toLowerCase();
    }

    private String encodeCharacter(char c) {
        return charMap.get(c) + "/";
    }

    private char decodeCharacter(String string) {
        return codeMap.get(string);
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
            decodedWord.append(decodeCharacter(codedChar));
        }
        return decodedWord.toString();
    }


}
