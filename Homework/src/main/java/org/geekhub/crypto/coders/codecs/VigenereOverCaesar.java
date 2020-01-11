package org.geekhub.crypto.coders.codecs;

import org.geekhub.crypto.annotations.Codec;
import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.coders.Decoder;
import org.geekhub.crypto.coders.Encoder;

@Codec(algorithm = Algorithm.VIGENERE_OVER_CAESAR)
public class VigenereOverCaesar implements Encoder, Decoder {
    private final Encoder vigenereEncoder;
    private final Encoder caesarEncoder;
    private final Decoder vigenereDecoder;
    private final Decoder caesarDecoder;

    public VigenereOverCaesar() {
        vigenereEncoder = new VigenereCodec();
        caesarEncoder = new CaesarCodec();
        vigenereDecoder = new VigenereCodec();
        caesarDecoder = new CaesarCodec();
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
