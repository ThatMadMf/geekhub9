package org.geekhub.crypto.coders;

public class EncodersFactory {

    public static Encoder getEncoder(String name) {
        if(name == null) {
            throw new IllegalArgumentException();
        }

        Algorithm algorithm = Algorithm.valueOf(name);

        switch (algorithm) {
            case MORSE:
                return new MorseCodec();
            case CAESAR:
                return new CaesarCodec();
            case VIGENERE:
                return new VigenereCodec();
            case VIGENERE_2X:
                return new Vigenere2x();
            case VIGENERE_2X_COMPOSITION:
                return new Vigenere2xComposition();
            case VIGENERE_OVER_CAESAR:
                return new VigenereOverCaesar();
            case UKRAINIAN_ENGLISH:
                return new UkrainianEnglish();
            default:
                throw new IllegalArgumentException("Invalid encoder");
        }
    }
}
