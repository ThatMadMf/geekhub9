package org.geekhub.crypto.coders;

import org.geekhub.crypto.coders.exception.FileProcessingFailedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class PropertiesReader {

    private final String path;

    public PropertiesReader(String configPath) {
        path = configPath;
    }

    public List<String> getMultipleValueProperty(String propertyName) {
        ClassLoader classLoader = PropertiesReader.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            Properties properties = new Properties();
            if (inputStream != null) {
                properties.load(inputStream);
                return Arrays.stream(properties.getProperty(propertyName).split(","))
                        .collect(Collectors.toList());
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return new ArrayList<>();
    }

    public String readSingleProperty(String propertyName) {
        ClassLoader classLoader = PropertiesReader.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            if (inputStream != null) {
                Properties properties = new Properties();
                properties.load(inputStream);
                return properties.getProperty(propertyName);
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return "";
    }

    public Map<String, String> readMap() {
        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(path)) {
            Map<String, String> result = new HashMap<>();
            Properties properties = new Properties();
            if (inputStream != null) {
                properties.load(new InputStreamReader(inputStream));

                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                    result.put(key, value);
                }
            }
            return result;
        } catch (IOException e) {
            throw new FileProcessingFailedException("Cannot read dictionary from properties");
        }
    }

}
