package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.util.ApplicationContextWrapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("classpath:config.properties")
public class DecodersFactory {

    public static Decoder getDecoder(Algorithm name) {
        if (name == null) {
            throw new CodecUnsupportedException("Unsupported encoder");
        }

        return ApplicationContextWrapper.getBean(name.name(), Decoder.class);
    }
}