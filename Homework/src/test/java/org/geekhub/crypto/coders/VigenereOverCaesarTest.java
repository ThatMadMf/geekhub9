package org.geekhub.crypto.coders;

import org.geekhub.crypto.coders.codecs.VigenereOverCaesar;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class VigenereOverCaesarTest {
    private Encoder encoder;
    private Decoder decoder;

    @BeforeGroups(groups = "encode")
    public void initialiseEncode() {
        encoder = new VigenereOverCaesar();
    }

    @BeforeGroups(groups = "decode")
    void initialiseDecode() {
        decoder = new VigenereOverCaesar();
    }

    @Test(groups = "encode", expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNull_Expect_EncodeFail() {
        encoder.encode(null);
    }

    @Test(groups = "encode", expectedExceptions = IllegalArgumentException.class)
    void When_EncodeEmptyWord_Expect_Exception() {
        encoder.encode("");
    }

    @Test(groups = "encode")
    void When_EncodeWord_Expect_Success() {
        String encodedWord = encoder.encode("geekhub");

        assertEquals(encodedWord, "fxrvkat");
    }

    @Test(groups = "encode")
    void When_EncodeText_Expect_Success() {
        String encodedWords = encoder.encode("geekhub THREE words");

        assertEquals(encodedWords, "fxrvkat SAEPH cgqwf");
    }

    @Test(groups = "decode", expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNull_Expect_DecodeFails() {
        decoder.decode(null);
    }

    @Test(groups = "decode", expectedExceptions = IllegalArgumentException.class)
    void When_DecodeEmptyWord_Expect_Exception() {
        decoder.decode("");
    }

    @Test(groups = "decode")
    void When_DecodeWord_Expect_Success() {
        String decodedWord = decoder.decode("fxrvkat");

        assertEquals(decodedWord, "geekhub");
    }

    @Test(groups = "decode")
    void When_DecodeText_Expect_Success() {
        String decodedWords = decoder.decode("fxrvkat SAEPH cgqwf");

        assertEquals(decodedWords, "geekhub THREE words");
    }

}