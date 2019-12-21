package org.geekhub.crypto.coders;

import org.geekhub.crypto.annotations.Codec;
import org.geekhub.crypto.annotations.Key;
import org.geekhub.crypto.annotations.Shift;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.exception.IllegalAnnotaionException;
import org.geekhub.crypto.logging.Logger;
import org.geekhub.crypto.logging.LoggerFactory;

import java.lang.reflect.Field;


public class DecodersFactory {
    private static final Class[] classes = new Class[]{CaesarCodec.class, MorseCodec.class, VigenereCodec.class,
            UkrainianEnglish.class, VigenereOverCaesar.class};


    public static Decoder getDecoder(Algorithm name) {
        if (name == null) {
            throw new CodecUnsupportedException("Unsupported decoder");
        }
        Logger compositeLogger = LoggerFactory.getLogger();

        for (Class currentClass : classes) {
            Codec codec = (Codec) currentClass.getAnnotation(Codec.class);
            if (codec.algorithm() == name && Decoder.class.isAssignableFrom(currentClass)) {
                try {
                    return initialiseFields((Decoder) currentClass.newInstance());
                } catch (InstantiationException | IllegalAccessException | IllegalAnnotaionException e) {
                    compositeLogger.warn(e.getMessage());
                    throw new CodecUnsupportedException("Cannot create such a decoder");
                }
            }
        }
        throw new CodecUnsupportedException("Cannot create such a decoder");
    }

    private static Decoder initialiseFields(Decoder decoder) {
        Shift shift = decoder.getClass().getAnnotation(Shift.class);
        Key key = decoder.getClass().getAnnotation(Key.class);
        try {
            if (shift != null) {
                Field field = decoder.getClass().getDeclaredField("shift");
                field.setAccessible(true);
                field.set(decoder, shift.shift());
            }
            if (key != null) {
                Field field = decoder.getClass().getDeclaredField("key");
                field.setAccessible(true);
                field.set(decoder, key.keyword());
            }
            return decoder;
        } catch (NoSuchFieldException e) {
            throw new IllegalAnnotaionException("Class does not support one of its annotations");
        } catch (IllegalAccessException e) {
            return decoder;
        }
    }
}
