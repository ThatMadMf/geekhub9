package org.geekhub.crypto.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class LoggerPropertiesReader {

    public static Set<LogDestination> getDestinations() {
        ClassLoader classLoader = LoggerFactory.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            if (inputStream != null) {
                properties.load(inputStream);
                return Arrays.stream(properties.getProperty("loggers").split(","))
                        .map(LogDestination::valueOf)
                        .collect(Collectors.toSet());
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Cannot create loggers");
        }
        return new HashSet<>();
    }
}
