package org.geekhub.crypto.coders;

import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class Vigenere2xTest {
    private Encoder encoder;
    private Decoder decoder;

    @BeforeGroups(groups = "encode")
    public void initialiseEncode() {
        encoder = new Vigenere2x();
    }

    @BeforeGroups(groups = "decode")
    public void initialiseDecode() {
        decoder = new Vigenere2x();
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "encode")
    void When_InputIsNull_Expect_EncodeFail() {
        encoder.encode(null);
    }

    @Test(groups = "encode", expectedExceptions = IllegalArgumentException.class)
    void When_EncodeEmptyWord_Expect_Exception() {
        String encodedWord = encoder.encode("");

        assertTrue(encodedWord.isEmpty());
    }

    @Test(groups = "encode")
    void When_EncodeWord_Expect_Success() {
        String encodedWord = encoder.encode("geekhub");

        assertEquals(encodedWord, "amacjch");
    }

    @Test(groups = "encode")
    void When_EncodeText_Expect_Success() {
        String encodedWords = encoder.encode("geekhub THREE words");

        assertEquals(encodedWords, "amacjch NPNWG eullo");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "decode")
    void When_InputIsNull_Expect_DecodeFails() {
        decoder.decode(null);
    }

    @Test(groups = "decode", expectedExceptions = IllegalArgumentException.class)
    void When_DecodeEmptyWord_Expect_Exception() {
        decoder.decode("");
    }

    @Test(groups = "decode")
    void When_DecodeWord_Expect_Success() {
        String decodedWord = decoder.decode("amacjch");

        assertEquals(decodedWord, "geekhub");
    }

    @Test(groups = "decode")
    void When_DecodeText_Expect_Success() {
        String decodedWords = decoder.decode("amacjch NPNWG eullo");

        assertEquals(decodedWords, "geekhub THREE words");
    }

}