package org.geehub.crypto.coders;

import org.geekhub.crypto.util.MapReverser;
import org.geekhub.crypto.util.PropertiesReader;
import org.geekhub.crypto.util.exception.IllegalInputException;

import java.util.Map;

public class Dictionary {
    private static final Map<String, String> ENGLISH_UKRAINIAN;
    private static final Map<String, String> UKRAINIAN_ENGLISH;

    static {
        PropertiesReader reader = new PropertiesReader("coders/TranslationDictionary.properties");
        ENGLISH_UKRAINIAN = reader.readMap();
        UKRAINIAN_ENGLISH = MapReverser.reverse(ENGLISH_UKRAINIAN);
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
