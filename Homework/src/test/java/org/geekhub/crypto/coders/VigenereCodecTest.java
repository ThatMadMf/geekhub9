package org.geekhub.crypto.coders;

import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class VigenereCodecTest {
    private Encoder encoder;
    private Decoder decoder;

    @BeforeGroups("encode")
    void initialiseEncode() {
        encoder = EncodersFactory.getEncoder("VIGENERE");
    }

    @BeforeGroups("decode")
    void initialiseDecode() {
        decoder = DecodersFactory.getDecoder("VIGENERE");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "encode")
    void When_InputIsNull_Expect_EncodeFail() {
        encoder.encode(null);
    }

    @Test(groups = "encode")
    void When_EncodeEmptyWord_Expect_Success() {
        String encodedWord = encoder.encode("");

        assertTrue(encodedWord.isEmpty());
    }

    @Test(groups = "encode")
    void When_EncodeWord_Expect_Success() {
        String encodedWord = encoder.encode("geekhub");

        assertEquals(encodedWord, "qicgvle");
    }

    @Test(groups = "encode")
    void When_EncodeText_Expect_Success() {
        String encodedWords = encoder.encode("geekhub THREE words");

        assertEquals(encodedWords, "qicgvle DLPAS nrbhq");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "decode")
    void When_InputIsNull_Expect_DecodeFails() {
        decoder.decode(null);
    }

    @Test(groups = "decode")
    void When_DecodeEmptyWord_Expect_Success() {
        String decodedWord = decoder.decode("");

        assertTrue(decodedWord.isEmpty());
    }

    @Test(groups = "decode")
    void When_DecodeWord_Expect_Success() {
        String decodedWord = decoder.decode("qicgvle");

        assertEquals(decodedWord, "geekhub");
    }

    @Test(groups = "decode")
    void When_DecodeText_Expect_Success() {
        String decodedWords = decoder.decode("qicgvle DLPAS nrbhq");

        assertEquals(decodedWords, "geekhub THREE words");
    }
}