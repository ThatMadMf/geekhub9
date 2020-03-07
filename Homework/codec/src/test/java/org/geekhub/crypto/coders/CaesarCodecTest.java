package org.geekhub.crypto.coders;

import org.geekhub.crypto.coders.codecs.CaesarCodec;
import org.geekhub.crypto.coders.exception.IllegalInputException;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CaesarCodecTest {
    private Encoder encoder;
    private Decoder decoder;

    @BeforeGroups(groups = "encode")
    public void initialiseEncode() {
        encoder = new CaesarCodec(15);
    }

    @BeforeGroups(groups = "decode")
    public void initialiseDecode() {
        decoder = new CaesarCodec(15);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "encode")
    void When_InputIsNull_Expect_Exception() {
        encoder.encode(null);
    }

    @Test(groups = "encode", expectedExceptions = IllegalArgumentException.class)
    void When_EncodeEmptyWord_Expect_Exception() {
        encoder.encode("");
    }

    @Test(groups = "encode", expectedExceptions = IllegalInputException.class)
    void When_EncodeNotSupportedCharacter_Expect_Exception() {
        encoder.encode("*un_supported%");

    }

    @Test(groups = "encode")
    void When_EncodeWord_Expect_Success() {
        String encodedWord = encoder.encode("geekhub");

        assertEquals(encodedWord, "vttzwjq");
    }

    @Test(groups = "encode")
    void When_EncodeText_Expect_Success() {
        String encodedWords = encoder.encode("geekhub THREE words!");

        assertEquals(encodedWords, "vttzwjq IWGTT ldgsh!");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "decode")
    void When_InputIsNull_Expect_DecodeFail() {
        decoder.decode(null);
    }

    @Test(groups = "decode", expectedExceptions = IllegalArgumentException.class)
    void When_DecodeEmptyWord_Expect_Exception() {
        decoder.decode("");
    }

    @Test(groups = "decode", expectedExceptions = IllegalInputException.class)
    void When_DecodeNotSupportedCharacter_Expect_Exception() {
        decoder.decode("*un_supported%");
    }

    @Test(groups = "decode")
    void When_DecodeWord_Expect_Success() {
        String decodedWord = decoder.decode("vttzwjq");

        assertEquals(decodedWord, "geekhub");
    }

    @Test(groups = "decode")
    void When_DecodeText_Expect_Success() {
        String decodedWords = decoder.decode("vttzwjq IWGTT ldgsh!");

        assertEquals(decodedWords, "geekhub THREE words!");
    }
}