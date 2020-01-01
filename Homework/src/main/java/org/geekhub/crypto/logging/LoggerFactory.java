package org.geekhub.crypto.logging;

import java.util.Set;

public class LoggerFactory {


    public static Logger getLogger() {
        Set<LogDestination> loggersEnum = LoggerPropertiesReader.getDestinations();
        return new CompositeLogger(loggersEnum);
    }

}
