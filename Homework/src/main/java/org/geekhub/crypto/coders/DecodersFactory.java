package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;


public class DecodersFactory {

    public static Decoder getDecoder(Algorithm name) {
        if (name == null) {
            throw new CodecUnsupportedException("Unsupported decoder");
        }
        Class[] classes = new Class[]{CaesarCodec.class, MorseCodec.class, VigenereCodec.class, UkrainianEnglish.class,
                VigenereOverCaesar.class};

        for (Class currentClass : classes) {
            Codec codec = (Codec) currentClass.getAnnotation(Codec.class);
            if (codec.algorithm() == name && Decoder.class.isAssignableFrom(currentClass)) {
                try {
                    return  (Decoder) currentClass.newInstance(); //requires no-args constructor
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new CodecUnsupportedException("Cannot create such a decoder");
                }
            }
        }
        throw new CodecUnsupportedException("Cannot create such a decoder");
    }
}
