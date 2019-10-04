package org.geekhub.lesson1.coders;

import org.geekhub.lesson1.util.NotImplementedException;

public class DecodersFactory {

    public static Decoder getDecoder(String name) {
        Algorithms algorithm = Algorithms.valueOf(name);

        switch (algorithm) {
            case MORSE:
                return  new MorseCodec();
            case CAESAR:
                return  new CaesarCodec();
            default:
                throw  new IllegalArgumentException("Invalid decoder");
        }
    }
}