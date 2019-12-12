package org.geekhub.crypto.coders;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class UkrainianEnglish implements Encoder, Decoder {
    private static final Dictionary DICTIONARY = new Dictionary();
    private static final String splitRegex = "[,.!?:]?\\s";
    private int currentMark = 0;

    @Override
    public String encode(String input) {
        inputCheck(input);
        String[] dividers = getPunctuationMarks(input);
        return Arrays.stream(getWords(input))
                .map(DICTIONARY::getEnglish)
                .collect(Collectors.joining(getCurrentMark(dividers)));
    }

    @Override
    public String decode(String input) {
        inputCheck(input);
        String[] dividers = getPunctuationMarks(input);
        return Arrays.stream(getWords(input))
                .map(DICTIONARY::getUkrainian)
                .collect(Collectors.joining(getCurrentMark(dividers)));
    }

    private void inputCheck(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input have to contain text");
        }
    }

    private String[] getWords(String input) {
        return input.split(splitRegex);
    }

    private String[] getPunctuationMarks(String input) {
        return Pattern.compile(splitRegex)
                .matcher(input)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);
    }

    private String getCurrentMark(String[] dividers) {
        if (dividers.length == 0) {
            return "";
        }
        return dividers[currentMark++];
    }

}
