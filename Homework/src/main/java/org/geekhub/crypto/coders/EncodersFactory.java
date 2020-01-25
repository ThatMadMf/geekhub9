package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan("org.geekhub.crypto.coders.codecs")
public class EncodersFactory {

    public static Encoder getEncoder(Algorithm name) {
        if (name == null) {
            throw new CodecUnsupportedException("Unsupported encoder");
        }

        try (ConfigurableApplicationContext context =
                     new AnnotationConfigApplicationContext(EncodersFactory.class)) {
            return context.getBean(name.name(), Encoder.class);
        }
    }
}
