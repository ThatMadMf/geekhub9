package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan("org.geekhub.crypto.coders.codecs")
public class EncodersFactory {

    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
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
