package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan("org.geekhub.crypto.coders")
public class DecodersFactory {

    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public static Decoder getDecoder(Algorithm name) {
        if (name == null) {
            throw new CodecUnsupportedException("Unsupported encoder");
        }

        try (ConfigurableApplicationContext context =
                     new AnnotationConfigApplicationContext(org.geekhub.crypto.coders.DecodersFactory.class)) {
            return context.getBean(name.name(), Decoder.class);
        }
    }
}