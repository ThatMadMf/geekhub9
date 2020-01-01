package org.geekhub.crypto.coders;

import org.geekhub.crypto.annotations.Codec;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.util.List;


public class DecodersFactory {
    public static Decoder getDecoder(Algorithm name) {
        if (name == null) {
            throw new CodecUnsupportedException("Unsupported decoder");
        }
        List<Class<?>> classes = ClassParser.getClasses();
        Logger logger = LoggerFactory.getLogger();

        for (Class<?> currentClass : classes) {
            Codec codec = currentClass.getAnnotation(Codec.class);
            if (codec.algorithm() == name && Decoder.class.isAssignableFrom(currentClass)) {
                try {
                    return (Decoder) FieldInitialiser.initialiseFields(currentClass.newInstance());
                } catch (IllegalAccessException | InstantiationException e) {
                    logger.warn(e.getMessage());
                }
            }
        }
        throw new CodecUnsupportedException("Cannot create such a decoder");
    }
}
