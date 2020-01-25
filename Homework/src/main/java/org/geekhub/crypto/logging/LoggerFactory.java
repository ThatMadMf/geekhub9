package org.geekhub.crypto.logging;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LoggerFactory {

    public static Logger getLoger() {
        try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(LogConfig.class)) {
            return context.getBean(CompositeLogger.class);
        }
    }
}
