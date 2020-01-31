package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.util.ApplicationContextWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class EncodersFactory {

    public static Encoder getEncoder(Algorithm name) {
        if (name == null) {
            throw new CodecUnsupportedException("Unsupported encoder");
        }

        return ApplicationContextWrapper.getBean(name.name(), Encoder.class);
    }
}
