package org.geekhub.lesson1.coders;

import org.geekhub.lesson1.util.NotImplementedException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

class MorseCodec implements Encoder, Decoder {

    private Map<Character, String> morseCodes = new Hashtable<>() {
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
        }
    };
    private boolean isLowerCase;

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

    private void caseTest (String input) {
        isLowerCase = Character.isLowerCase(input.charAt(0)) ? true : false;
        for (int i = 0; i < input.length(); i++) {
            if(isLowerCase) {
                if(Character.isUpperCase(input.charAt(i))) {
                    System.exit(1);
                }
            } else {
                if(Character.isLowerCase(input.charAt(i))) {
                    System.exit(1);
                }
            }
        }
    }

    @Override
    public String encode(String input) {
        caseTest(input);
        String[] words = input.toUpperCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < words.length; i++) {
            char[] word = words[i].toCharArray();
            for(int j = 0; j < word.length; j++) {
                if(j != 0 && j < word.length) {
                    result.append(" ");
                }
                result.append(encodeCharacter(word[j]));
            }
            if(i < words.length - 1) {
                result.append(" / ");
            }
        }
        return  result.toString();
    }

    @Override
    public String decode(String input) {
        String[] words = input.split("(\\s+\\/\\s)");
        StringBuilder result = new StringBuilder();
        for(String str : words) {
            String[] codedChars = str.split("\\s+");
            for (String codedChar : codedChars) {
                result.append(decodeCharacter(codedChar));
            }
            result.append(" ");
        }
        result.setLength(result.length() - 1);
        if(isLowerCase) {
            return result.toString().toLowerCase();
        } else {
            return  result.toString();
        }
    }

}
