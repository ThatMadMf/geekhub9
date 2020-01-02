package org.geekhub.crypto.logging;

import org.geekhub.crypto.util.PropertiesReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoggerFactory {


    public static Logger getLogger() {
        PropertiesReader propertiesReader = new PropertiesReader("config.properties");
        List<String> readProperties = propertiesReader.getMultipleValueProperty("loggers");
        Set<LogDestination> destinations = new HashSet<>();
        for(String property : readProperties) {
            destinations.add(LogDestination.valueOf(property));
        }

        return new CompositeLogger(destinations);
    }

}
