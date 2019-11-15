package org.geekhub.crypto.coders;

class Vigenere2xComposition implements Encoder, Decoder {
    private final Encoder vigenereEncoder;
    private final Decoder vigenereDecoder;

    public Vigenere2xComposition() {
        vigenereEncoder = EncodersFactory.getEncoder("VIGENERE");
        vigenereDecoder = DecodersFactory.getDecoder("VIGENERE");
    }

    public String encode(String input) {
        String result = vigenereEncoder.encode(input);
        result = vigenereEncoder.encode(result);
        return result;
    }

    public String decode(String input) {
        String result = vigenereDecoder.decode(input);
        result = vigenereDecoder.decode(result);
        return result;
    }
}
