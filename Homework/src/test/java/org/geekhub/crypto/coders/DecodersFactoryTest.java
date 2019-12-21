package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DecodersFactoryTest {

    @Test(expectedExceptions = CodecUnsupportedException.class)
    void When_InputIsNull_Expect_Exception() {
        DecodersFactory.getDecoder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNotExistingElement_Expect_Exception() {
        DecodersFactory.getDecoder(Algorithm.valueOf("NotExistingAlgorithm"));
    }

    @Test()
    void Whent_InputIsMorse_Expect_Success() {
        Decoder decoder = DecodersFactory.getDecoder(Algorithm.MORSE);

        assertTrue(decoder instanceof MorseCodec);
    }


    @Test()
    void Whent_InputIsCaesar_Expect_Success() {
        Decoder decoder = DecodersFactory.getDecoder(Algorithm.CAESAR);

        assertTrue(decoder instanceof CaesarCodec);
    }


    @Test()
    void Whent_InputIsVigenere_Expect_Success() {
        Decoder decoder = DecodersFactory.getDecoder(Algorithm.VIGENERE);

        assertTrue(decoder instanceof VigenereCodec);
    }


    @Test()
    void Whent_InputIsVigenereOverCasesar_Expect_Success() {
        Decoder decoder = DecodersFactory.getDecoder(Algorithm.VIGENERE_OVER_CAESAR);

        assertTrue(decoder instanceof VigenereOverCaesar);
    }


    @Test()
    void Whent_InputIsUkranianEnglish_Expect_Success() {
        Decoder decoder = DecodersFactory.getDecoder(Algorithm.UKRAINIAN_ENGLISH);

        assertTrue(decoder instanceof UkrainianEnglish);
    }

}