package org.geekhub.crypto.coders;

import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UkrainianEnglishTest {
    private Encoder encoder;
    private Decoder decoder;

    @BeforeGroups(groups = "encode")
    void initialiseEncode() {
        encoder = EncodersFactory.getEncoder("UKRAINIAN_ENGLISH");
    }

    @BeforeGroups(groups = "decode")
    void initialiseDecode() {
        decoder = DecodersFactory.getDecoder("UKRAINIAN_ENGLISH");
    }

    @Test(groups = "encode", expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNull_Expect_Exception() {
        encoder.encode(null);
    }

    @Test(groups = "encode", expectedExceptions = IllegalArgumentException.class)
    void When_InputIsBlank_Expect_Exception() {
        encoder.encode("");
    }

    @Test(groups = "encode")
    void When_EncodeNotSupportedWord_Expect_Exception() {
        String encoded = encoder.encode("непідтримуємеслово");
        assertEquals("Unsupported word[непідтримуємеслово]", encoded);
    }

    @Test(groups = "encode")
    void When_EncodeWord_Expect_Success() {
        String encoded = encoder.encode("діяти");

        assertEquals(encoded, "act");
    }

    @Test(groups = "encode")
    void When_EncodeText_Expect_Success() {
        String encoded = encoder.encode("тест слово побачити");

        assertEquals(encoded, "test word see");
    }

    @Test(groups = "decode", expectedExceptions = IllegalArgumentException.class)
    void When_InputDecodeIsNull_Expect_Exception() {
        decoder.decode(null);
    }

    @Test(groups = "decode")
    void When_DecodeNotSupportedWord_Expect_Exception() {
        String decoded = decoder.decode("notsupportedword");
        assertEquals("Unsupported word[notsupportedword]", decoded);
    }

    @Test(groups = "decode", expectedExceptions = IllegalArgumentException.class)
    void When_InputDecodeIsBlank_Expect_Exception() {
        decoder.decode("");
    }

    @Test(groups = "decode")
    void When_DecodeWord_Expect_Success() {
        String decoded = decoder.decode("test");

        assertEquals(decoded, "тест");
    }

    @Test(groups = "decode")
    void When_DecodeText_Expect_Success() {
        String decoded = decoder.decode("test word see");

        assertEquals(decoded, "тест слово побачити");
    }
}