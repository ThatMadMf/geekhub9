package org.geekhub.crypto.coders;

import com.google.gson.Gson;
import org.geekhub.crypto.annotations.Codec;
import org.geekhub.crypto.exception.IllegalInputException;
import org.geekhub.crypto.model.translation.TranslationModel;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Codec(algorithm = Algorithm.UKRAINIAN_ENGLISH)
class UkrainianEnglish implements Encoder, Decoder {
    private static final Dictionary DICTIONARY = new Dictionary();
    private static final String SPLIT_REGEX = "[,.!?:\\s]+|$";
    private final String key;
    private static final Logger compositeLogger = LoggerFactory.getLogger();

    public UkrainianEnglish(String key) {
        this.key = key;
    }

    public UkrainianEnglish() {
        key = "key=AIzaSyB2HijQLlsmI1udH9ARl45oC5eAj4XfjTw";
    }

    @Override
    public String encode(String input) {
        inputNullCheck(input);

        String translateOnline = encodeOnline(input);
        if (translateOnline == null) {
            return encodeOffline(input);
        } else {
            return translateOnline;
        }
    }

    @Override
    public String decode(String input) {
        inputNullCheck(input);

        String translateOnline = decodeOnline(input);
        if (translateOnline == null) {
            return decodeOffline(input);
        } else {
            return translateOnline;
        }
    }

    private String encodeOffline(String input) {
        Deque<String> dividers = getPunctuationMarks(input);
        return Arrays.stream(getWords(input))
                .map(DICTIONARY::getEnglish)
                .map(w -> getCurrentMark(w, dividers))
                .collect(Collectors.joining());
    }

    private String decodeOffline(String input) {
        Deque<String> dividers = getPunctuationMarks(input);
        return Arrays.stream(getWords(input))
                .map(DICTIONARY::getUkrainian)
                .map(w -> getCurrentMark(w, dividers))
                .collect(Collectors.joining());
    }

    private String encodeOnline(String input) {
        String query = key + "&source=uk" + "&target=en" + "&q=" + input.replace(" ", "%20");
        return makeRequest(query);
    }

    private String decodeOnline(String input) {
        String query = key + "&source=en" + "&target=uk" + "&q=" + input.replace(" ", "%20");
        return makeRequest(query);
    }

    private String makeRequest(String query) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.googleapis.com/language/translate/v2?" + query))
                    .header("Referer", "https://www.daytranslations.com/free-translation-online/")
                    .GET()
                    .build();

            Gson gson = new Gson();

            TranslationModel translatedText = gson.fromJson(HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString())
                    .body(), TranslationModel.class);
            return translatedText.getData().getTranslations().get(0).getTranslatedText();

        } catch (IllegalArgumentException e) {
            throw new IllegalInputException("Unsupported character");
        } catch (InterruptedException | NullPointerException e) {
            compositeLogger.warn("Failed to make request");
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            compositeLogger.warn("Failed to translate online");
        }
        return null;
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
