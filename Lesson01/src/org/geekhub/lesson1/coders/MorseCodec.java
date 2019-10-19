package org.geekhub.lesson1.coders;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

class MorseCodec implements Encoder, Decoder {

    private static final Map<Character, String> morseCodes = codesInitialize();


    private static Map codesInitialize() {
        return new Hashtable<>() {
            {
                put('A', ".-");
                put('B', "-...");
                put('C', "-.-.");
                put('D', "-..");
                put('E', ".");
                put('F', "..-.");
                put('G', "--.");
                put('H', "....");
                put('I', "..");
                put('J', ".---");
                put('K', "-.-");
                put('L', ".-..");
                put('M', "--");
                put('N', "-.");
                put('O', "---");
                put('P', ".--.");
                put('Q', "--.-");
                put('R', ".-.");
                put('S', "...");
                put('T', "-");
                put('U', "..-");
                put('V', "...-");
                put('W', ".--");
                put('X', "-..-");
                put('Y', "-.--");
                put('Z', "--..");
                put('1', ".----");
                put('2', "..---");
                put('3', "...--");
                put('4', "....-");
                put('5', ".....");
                put('6', "-....");
                put('7', "--...");
                put('8', "---..");
                put('9', "----.");
                put('0', "-----");
                put(',', "--..--");
                put('?', "..--..");
                put('-', "-....-");
            }
        };
    }

    private String encodeCharacter(char c) {
        return morseCodes.get(c);
    }

    private char decodeCharacter(String string) {
        for (Map.Entry<Character, String> entry : morseCodes.entrySet()) {
            if (Objects.equals(string, entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Cannot find character");
    }

    private String encodeWord(String input) {
        StringBuilder codedWord = new StringBuilder();
        char[] word = input.toCharArray();

        for (int j = 0; j < word.length; j++) {
            if (j != 0 && j < word.length) {
                codedWord.append(" ");
            }
            codedWord.append(encodeCharacter(word[j]));
        }
        return codedWord.toString();
    }

    private String decodedWord(String input) {
        StringBuilder decodedWord = new StringBuilder();
        String[] codedChars = input.split("\\s+");

        for (String codedChar : codedChars) {
            decodedWord.append(decodeCharacter(codedChar));
        }

        return decodedWord.toString();
    }

    @Override
    public String encode(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                System.out.println("Error. You have to use characters of single case.");
                System.exit(1);
            }
        }

        String[] words = input.toUpperCase().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(encodeWord(word));
            result.append(" / ");
        }

        result.setLength(result.length() - 3);
        return result.toString();
    }

    @Override
    public String decode(String input) {
        String[] words = input.split("(\\s+\\/\\s)");
        StringBuilder result = new StringBuilder();

        for (String str : words) {
            result.append(decodedWord(str));
            result.append(" ");
        }

        result.setLength(result.length() - 1);
        return result.toString().toLowerCase();
    }
}
