package org.geekhub.crypto.coders;

import org.geekhub.crypto.util.IllegalCharacterException;

import java.util.Arrays;
import java.util.stream.Collectors;

class UkrainianEnglish implements Encoder, Decoder {
    private static final Dictionary DICTIONARY = new Dictionary();
    private static final String WHITE_SPACE = " ";

    @Override
    public String encode(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("input should not be blank");
        }
        try {
            return Arrays.stream(input.split(WHITE_SPACE))
                    .map(DICTIONARY::getEnglish)
                    .collect(Collectors.joining(WHITE_SPACE));
        } catch (IllegalCharacterException e) {
            return e.getMessage();
        }
    }

    @Override
    public String decode(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("input should not be blank");
        }
        try {
            return Arrays.stream(input.split(WHITE_SPACE))
                    .map(DICTIONARY::getUkrainian)
                    .collect(Collectors.joining(WHITE_SPACE));
        } catch (IllegalCharacterException e) {
            return e.getMessage();
        }
    }
}
