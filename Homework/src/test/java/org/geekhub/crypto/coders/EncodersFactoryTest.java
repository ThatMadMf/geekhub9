package org.geekhub.crypto.coders;

import org.geekhub.crypto.coders.codecs.*;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class EncodersFactoryTest {

    @Test(expectedExceptions = CodecUnsupportedException.class)
    void When_InputIsNull_ThrowingException() {
        EncoderFactory.getEncoder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNotExistingElement_ThrowingException() {
        EncoderFactory.getEncoder(Algorithm.valueOf("NotExistingAlgorithm"));
    }

    @Test()
    void Whent_InputIsMorse_Expect_Success() {
        Encoder encoder = EncoderFactory.getEncoder(Algorithm.MORSE);

        assertTrue(encoder instanceof MorseCodec);
    }


    @Test()
    void Whent_InputIsCaesar_Expect_Success() {
        Encoder encoder = EncoderFactory.getEncoder(Algorithm.CAESAR);

        assertTrue(encoder instanceof CaesarCodec);
    }


    @Test()
    void Whent_InputIsVigenere_Expect_Success() {
        Encoder encoder = EncoderFactory.getEncoder(Algorithm.VIGENERE);

        assertTrue(encoder instanceof VigenereCodec);
    }


    @Test()
    void Whent_InputIsVigenereOverCasesar_Expect_Success() {
        Encoder encoder = EncoderFactory.getEncoder(Algorithm.VIGENERE_OVER_CAESAR);

        assertTrue(encoder instanceof VigenereOverCaesar);
    }


    @Test()
    void Whent_InputIsUkranianEnglish_Expect_Success() {
        Encoder encoder = EncoderFactory.getEncoder(Algorithm.UKRAINIAN_ENGLISH);

        assertTrue(encoder instanceof UkrainianEnglish);
    }

}