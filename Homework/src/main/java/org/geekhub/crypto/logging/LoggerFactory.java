package org.geekhub.crypto.logging;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoggerFactory {

    public static Logger getLoger() {
        try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(LogConfig.class)) {
            return context.getBean(CompositeLogger.class);
        }
    }
}
