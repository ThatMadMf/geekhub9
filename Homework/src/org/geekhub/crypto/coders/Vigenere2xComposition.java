package org.geekhub.crypto.coders;

class Vigenere2xComposition implements Encoder, Decoder {
    private VigenereCodec vigenereCodec;

    public Vigenere2xComposition() {
        vigenereCodec = new VigenereCodec();
    }

    public String encode(String input) {
        String result = vigenereCodec.encode(input);
        result = vigenereCodec.encode(result);
        return result;
    }

    public String decode(String input) {
        String result = vigenereCodec.decode(input);
        result = vigenereCodec.decode(result);
        return result;
    }
}
