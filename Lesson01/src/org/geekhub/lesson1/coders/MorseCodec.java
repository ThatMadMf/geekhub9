package org.geekhub.lesson1.coders;

import org.geekhub.lesson1.util.NotImplementedException;

import java.util.ArrayList;
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
        }
    };

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

    @Override
    public String encode(String input) {
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
        return  result.toString().toLowerCase();
    }

}
