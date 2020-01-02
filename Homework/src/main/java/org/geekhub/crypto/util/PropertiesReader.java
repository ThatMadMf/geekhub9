package org.geekhub.crypto.util;

import java.io.IOException;
import java.io.InputStream;
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
            System.out.println("Cannot read property " + propertyName);
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
            System.out.println("Cannot read property " + propertyName);
        }
        return "";
    }

}
