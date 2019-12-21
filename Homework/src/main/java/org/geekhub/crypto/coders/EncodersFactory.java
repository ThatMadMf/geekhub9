package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;

public class EncodersFactory {

    public static Encoder getEncoder(Algorithm name) {
        if(name == null) {
            throw new CodecUnsupportedException("Unsupported encoder");
        }
        Class[] classes = new Class[]{CaesarCodec.class, MorseCodec.class, VigenereCodec.class, UkrainianEnglish.class,
                VigenereOverCaesar.class};

        for (Class currentClass : classes) {
            Codec codec = (Codec) currentClass.getAnnotation(Codec.class);
            if (codec.algorithm() == name && Encoder.class.isAssignableFrom(currentClass)) {
                try {
                    return  (Encoder) currentClass.newInstance(); //requires no-args constructor
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new CodecUnsupportedException("Cannot create such a decoder");
                }
            }
        }
        throw new CodecUnsupportedException("Cannot create such a decoder");
    }
}
