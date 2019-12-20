package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.FileProcessingFailedException;
import org.geekhub.crypto.exception.IllegalInputException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Dictionary {
    private static final Map<String, String> ENGLISH_UKRAINIAN;
    private static final Map<String, String> UKRAINIAN_ENGLISH;

    static {
        ENGLISH_UKRAINIAN = new HashMap<>();
        UKRAINIAN_ENGLISH = new HashMap<>();
        ClassLoader classLoader = Dictionary.class.getClassLoader();
        try (InputStream inputStream =
                     classLoader.getResourceAsStream("coders/TranslationDictionary.properties")) {
            Properties properties = new Properties();
            if (inputStream != null) {
                properties.load(new InputStreamReader(inputStream));

                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                    ENGLISH_UKRAINIAN.put(key, value);
                    UKRAINIAN_ENGLISH.put(value, key);
                }
            }
        } catch (IOException | ClassCastException e) {
            throw new FileProcessingFailedException("Error during reading dictionary map from properties");
        }
    }

    public String getUkrainian(String input) {
        String translated = ENGLISH_UKRAINIAN.get(input);
        if (translated == null) {
            throw new IllegalInputException("Word is absent in dictionary: " + input);
        }
        return translated;
    }

    public String getEnglish(String input) {
        String translated = UKRAINIAN_ENGLISH.get(input);
        if (translated == null) {
            throw new IllegalInputException("Word is absent in dictionary: " + input);
        }
        return translated;
    }
}
