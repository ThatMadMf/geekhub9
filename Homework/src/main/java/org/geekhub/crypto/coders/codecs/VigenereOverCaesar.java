package org.geekhub.crypto.coders.codecs;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.coders.Decoder;
import org.geekhub.crypto.coders.Encoder;
import org.springframework.stereotype.Component;

@Component
public class VigenereOverCaesar implements Encoder, Decoder {
    private final Encoder vigenereEncoder;
    private final Encoder caesarEncoder;
    private final Decoder vigenereDecoder;
    private final Decoder caesarDecoder;

    public VigenereOverCaesar(CaesarCodec caesarCodec, VigenereCodec vigenereCodec) {
        vigenereEncoder = vigenereCodec;
        caesarEncoder = caesarCodec;
        vigenereDecoder = vigenereCodec;
        caesarDecoder = caesarCodec;
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

    @Override
    public Algorithm getAlgorithm() {
        return Algorithm.VIGENERE_OVER_CAESAR;
    }
}
