package org.geekhub.crypto.coders;

public class EncodersFactory {

    public static Encoder getEncoder(String name) {
        Algorithms algorithm = Algorithms.valueOf(name);

        switch (algorithm) {
            case MORSE:
                return new MorseCodec();
            case CAESAR:
                return new CaesarCodec();
            case VIGENERE:
                return new VigenereCodec();
            case VIGENERE2X:
                return new Vigenere2x();
            case VIGENERE2XCOMPOSITION:
                return new Vigenere2xComposition();
            default:
                throw new IllegalArgumentException("Invalid encoder");
        }
    }
}
