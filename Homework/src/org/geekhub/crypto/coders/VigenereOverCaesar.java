package org.geekhub.crypto.coders;

class VigenereOverCaesar implements Encoder, Decoder {
    private VigenereCodec vigenereCodec;
    private CaesarCodec caesarCodec;

    public VigenereOverCaesar() {
        vigenereCodec = new VigenereCodec();
        caesarCodec = new CaesarCodec();
    }

    @Override
    public String encode(String input) {
        String result = caesarCodec.encode(input);
        return vigenereCodec.encode(result);
    }

    @Override
    public String decode(String input) {
        String result = vigenereCodec.decode(input);
        return caesarCodec.decode(result);
    }


}
