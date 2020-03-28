package org.geekhub.crypto.coders.codecs;

import org.geekhub.crypto.coders.*;
import org.geekhub.crypto.coders.exception.IllegalInputException;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class UkrainianEnglish implements Encoder, Decoder {

    private static final String SPLIT_REGEX = "[,.!?:\\s]+|$";
    private final Dictionary dictionary;

    public UkrainianEnglish(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public String encode(String input) {
        inputNullCheck(input);

        List<String> cachedValue = dictionary.getEnglish(getWords(input));
        if (cachedValue.contains(null)) {
            return TranslateApi.ukrainianToEnglish(input);
        } else {

            Deque<String> dividers = getPunctuationMarks(input);
            return cachedValue.stream()
                    .map(w -> getCurrentMark(w, dividers))
                    .collect(Collectors.joining());
        }
    }

    @Override
    public String decode(String input) {
        inputNullCheck(input);

        List<String> cachedValue = dictionary.getUkrainian(getWords(input));
        if (cachedValue.contains(null)) {
            return TranslateApi.englishToUkrainian(input);
        } else {

            Deque<String> dividers = getPunctuationMarks(input);
            return cachedValue.stream()
                    .map(w -> getCurrentMark(w, dividers))
                    .collect(Collectors.joining());
        }
    }

    @Override
    public Algorithm getAlgorithm() {
        return Algorithm.UKRAINIAN_ENGLISH;
    }

    private void inputNullCheck(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalInputException("Input have to contain text");
        }
    }

    private String[] getWords(String input) {
        return input.split(SPLIT_REGEX);
    }

    private Deque<String> getPunctuationMarks(String input) {
        return Pattern.compile(SPLIT_REGEX)
                .matcher(input)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    private String getCurrentMark(String word, Deque<String> dividers) {
        if (dividers.isEmpty()) {
            return "";
        }
        return word + dividers.pollFirst();
    }


}
