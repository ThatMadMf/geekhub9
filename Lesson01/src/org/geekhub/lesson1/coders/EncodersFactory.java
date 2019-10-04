package org.geekhub.lesson1.coders;

import org.geekhub.lesson1.util.NotImplementedException;

public class EncodersFactory {

    public static Encoder getEncoder(String name) {
        Algorithms algorithm = Algorithms.valueOf(name);

        switch (algorithm) {
            case MORSE:
                return new MorseCodec();
            case CAESAR:
                return  new CaesarCodec();
            default:
                throw new IllegalArgumentException("Invalid encoder");
        }
    }
}
