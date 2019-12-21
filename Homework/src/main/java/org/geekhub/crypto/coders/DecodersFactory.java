package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;

public class DecodersFactory {

    public static Decoder getDecoder(Algorithm name) {
        if(name == null) {
            throw new CodecUnsupportedException("Unsupported decoder");
        }
        switch (name) {
            case MORSE:
                return new MorseCodec();
            case CAESAR:
                return new CaesarCodec();
            case VIGENERE:
                return new VigenereCodec();
            case VIGENERE_OVER_CAESAR:
                return new VigenereOverCaesar();
            case UKRAINIAN_ENGLISH:
                return new UkrainianEnglish();
            default:
                throw new CodecUnsupportedException("Unsupported decoder[" + name + "]");
        }
    }
}
