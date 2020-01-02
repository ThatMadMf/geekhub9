package org.geekhub.crypto.coders;

import org.geekhub.crypto.annotations.Codec;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class EncodersFactory {

    public static Encoder getEncoder(Algorithm name) {
        if (name == null) {
            throw new CodecUnsupportedException("Unsupported encoder");
        }
        Logger logger = LoggerFactory.getLogger();
        List<Class<?>> classes = ClassParser.matchingClasses;

        for (Class<?> currentClass : classes) {
            Codec codec = currentClass.getAnnotation(Codec.class);
            if (codec.algorithm() == name && Encoder.class.isAssignableFrom(currentClass)) {
                try {
                    return (Encoder) FieldInitialiser.initialiseFields(
                            currentClass.getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException |
                        NoSuchMethodException | InvocationTargetException e) {
                    logger.warn(e.getMessage());
                }
            }
        }
        throw new CodecUnsupportedException("Cannot create such an encoder");
    }
}
