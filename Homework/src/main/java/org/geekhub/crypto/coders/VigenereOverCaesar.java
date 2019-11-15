package main.java.org.geekhub.crypto.coders;

class VigenereOverCaesar implements Encoder, Decoder {
    private final Encoder vigenereEncoder;
    private final Encoder caesarEncoder;
    private final Decoder vigenereDecoder;
    private final Decoder caesarDecoder;

    public VigenereOverCaesar() {
        vigenereEncoder = EncodersFactory.getEncoder("VIGENERE");
        caesarEncoder = EncodersFactory.getEncoder("CAESAR");
        vigenereDecoder = DecodersFactory.getDecoder("VIGENERE");
        caesarDecoder = DecodersFactory.getDecoder("CAESAR");
    }

    @Override
    public String encode(String input) {
        String result = caesarEncoder.encode(input);
        return vigenereEncoder.encode(result);
    }

    @Override
    public String decode(String input) {
        String result = vigenereDecoder.decode(input);
        return caesarDecoder.decode(result);
    }


}
