package org.geekhub.crypto.coders.codecs;

import org.geekhub.crypto.coders.Decoder;
import org.geekhub.crypto.coders.Encoder;
import org.springframework.stereotype.Component;

@Component("VIGENERE_OVER_CAESAR")
public class VigenereOverCaesar implements Encoder, Decoder {
    private final Encoder vigenereEncoder;
    private final Encoder caesarEncoder;
    private final Decoder vigenereDecoder;
    private final Decoder caesarDecoder;

    public VigenereOverCaesar() {
        vigenereEncoder = new VigenereCodec("notkeyword");
        caesarEncoder = new CaesarCodec(20);
        vigenereDecoder = new VigenereCodec("notkeyword");
        caesarDecoder = new CaesarCodec(20);
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
