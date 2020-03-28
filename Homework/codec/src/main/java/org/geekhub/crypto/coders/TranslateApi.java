package org.geekhub.crypto.coders;

import com.google.gson.Gson;
import org.geekhub.crypto.coders.exception.FileProcessingFailedException;
import org.geekhub.crypto.coders.exception.IllegalInputException;
import org.geekhub.crypto.coders.model.translation.TranslationModel;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TranslateApi {

    private static final String KEY = "key=AIzaSyB2HijQLlsmI1udH9ARl45oC5eAj4XfjTw";

    public static String ukrainianToEnglish(String input) {
        String query = KEY + "&source=uk" + "&target=en" + "&q=" + input.replace(" ", "%20");
        return makeRequest(query);
    }

    public static String englishToUkrainian(String input) {
        String query = KEY + "&source=en" + "&target=uk" + "&q=" + input.replace(" ", "%20");
        return makeRequest(query);
    }

    private static String makeRequest(String query) {
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
            if (translatedText.getData() != null) {
                return translatedText.getData().getTranslations().get(0).getTranslatedText();
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalInputException("Unsupported character");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new FileProcessingFailedException("Failed to make request", e);
        } catch (IOException e) {
            throw new FileProcessingFailedException("Failed to translate online", e);
        }
        return null;
    }
}
