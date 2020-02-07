package org.geekhub.crypto.coders;

import org.geekhub.crypto.coders.codecs.*;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Component
public class DecodersFactoryTest {

    private DecoderFactory decoderFactory;

    @Autowired
    public DecodersFactoryTest(DecoderFactory decoderFactory) {
        this.decoderFactory = decoderFactory;
    }

    @Test(expectedExceptions = CodecUnsupportedException.class)
    void When_InputIsNull_Expect_Exception() {
        decoderFactory.getDecoder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNotExistingElement_Expect_Exception() {
        decoderFactory.getDecoder(Algorithm.valueOf("NotExistingAlgorithm"));
    }

    @Test()
    void Whent_InputIsMorse_Expect_Success() {
        Decoder decoder = decoderFactory.getDecoder(Algorithm.MORSE);

        assertTrue(decoder instanceof MorseCodec);
    }


    @Test()
    void Whent_InputIsCaesar_Expect_Success() {
        Decoder decoder = decoderFactory.getDecoder(Algorithm.CAESAR);

        assertTrue(decoder instanceof CaesarCodec);
    }


    @Test()
    void Whent_InputIsVigenere_Expect_Success() {
        Decoder decoder = decoderFactory.getDecoder(Algorithm.VIGENERE);

        assertTrue(decoder instanceof VigenereCodec);
    }


    @Test()
    void Whent_InputIsVigenereOverCasesar_Expect_Success() {
        Decoder decoder = decoderFactory.getDecoder(Algorithm.VIGENERE_OVER_CAESAR);

        assertTrue(decoder instanceof VigenereOverCaesar);
    }


    @Test()
    void Whent_InputIsUkranianEnglish_Expect_Success() {
        Decoder decoder = decoderFactory.getDecoder(Algorithm.UKRAINIAN_ENGLISH);

        assertTrue(decoder instanceof UkrainianEnglish);
    }

}