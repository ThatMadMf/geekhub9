package org.geekhub.crypto.coders;

import org.geekhub.crypto.annotations.Codec;
import org.geekhub.crypto.annotations.Key;
import org.geekhub.crypto.annotations.Shift;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.exception.IllegalAnnotaionException;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.lang.reflect.Field;

public class EncodersFactory {

    public static Encoder getEncoder(Algorithm name) {
        if (name == null) {
            throw new CodecUnsupportedException("Unsupported encoder");
        }
        Logger compositeLogger = LoggerFactory.getLogger();

        Class[] classes = new Class[]{CaesarCodec.class, MorseCodec.class, VigenereCodec.class, UkrainianEnglish.class,
                VigenereOverCaesar.class};
        for (Class currentClass : classes) {
            Codec codec = (Codec) currentClass.getAnnotation(Codec.class);
            if (codec.algorithm() == name && Encoder.class.isAssignableFrom(currentClass)) {
                try {
                    return initialiseFields((Encoder) currentClass.newInstance());
                } catch (InstantiationException | IllegalAccessException | IllegalAnnotaionException e) {
                    compositeLogger.warn(e.getMessage());
                    throw new CodecUnsupportedException("Cannot create such a decoder");
                }
            }
        }
        throw new CodecUnsupportedException("Cannot create such a decoder");
    }

    private static Encoder initialiseFields(Encoder encoder) {
        Shift shift = encoder.getClass().getAnnotation(Shift.class);
        Key key = encoder.getClass().getAnnotation(Key.class);
        try {
            if (shift != null) {
                Field field = encoder.getClass().getDeclaredField("shift");
                field.setAccessible(true);
                field.set(encoder, shift.shift());
            }
            if (key != null) {
                Field field = encoder.getClass().getDeclaredField("key");
                field.setAccessible(true);
                field.set(encoder, key.keyword());
            }
            return encoder;
        } catch (NoSuchFieldException e) {
            throw new IllegalAnnotaionException("Class does not support one of its annotations");
        } catch (IllegalAccessException e) {
            return encoder;
        }
    }
}
