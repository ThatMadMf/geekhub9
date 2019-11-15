package org.geekhub.crypto.coders;

class Vigenere2x extends VigenereCodec {

    @Override
    public String encode(String input) {
        String encoded = super.encode(input);
        encoded = super.encode(encoded);
        return encoded;
    }

    @Override
    public String decode(String input) {
        String decoded = super.decode(input);
        return super.decode(decoded);
    }
}
