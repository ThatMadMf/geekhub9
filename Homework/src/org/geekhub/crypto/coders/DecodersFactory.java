package org.geekhub.crypto.coders;

public class DecodersFactory {

    public static Decoder getDecoder(String name) {
        Algorithms algorithm = Algorithms.valueOf(name);

        switch (algorithm) {
            case MORSE:
                return new MorseCodec();
            case CAESAR:
                return new CaesarCodec();
            case VIGENERE:
                return new VigenereCodec();
            case VIGENERE_2X:
                return new Vigenere2x();
            case VIGENERE_OVER_CAESAR:
                return new VigenereOverCaesar();
            default:
                throw new IllegalArgumentException("Invalid decoder");
        }
    }
}
