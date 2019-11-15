package org.geekhub.crypto.coders;

import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MorseCodecTest {
    private Encoder encoder;
    private Decoder decoder;

    @BeforeGroups("encode")
    void initialiseEncode() {
        encoder = EncodersFactory.getEncoder("MORSE");
    }

    @BeforeGroups("decode")
    void initialiseDecode() {
        decoder = DecodersFactory.getDecoder("MORSE");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "encode")
    void When_InputIsNull_Expect_EncodeFail() {
        encoder.encode(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "encode")
    void When_InputNotInSingleCase_Expect_EncodeFail() {
        encoder.encode("WOrd");
    }

    @Test(groups = "encode")
    void When_EncodeEmptyWord_Expect_Success() {
        String encodedWord = encoder.encode("");

        assertTrue(encodedWord.isEmpty());
    }

    @Test(groups = "encode")
    void When_EncodeWord_Expect_Success() {
        String encodedWord = encoder.encode("geekhub");

        assertEquals(encodedWord, "--./././-.-/..../..-/-.../");
    }

    @Test(groups = "encode")
    void When_EncodeText_Expect_Success() {
        String encodedWords = encoder.encode("geekhub words");

        assertEquals(encodedWords, "--./././-.-/..../..-/-.../......./.--/---/.-./-../.../");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "decode")
    void When_InputIsNull_Expect_DecodeFail() {
        decoder.decode(null);
    }

    @Test(groups = "decode")
    void When_DecodeEmptyWord_Expect_Success() {
        String decodedWord = decoder.decode("");

        assertTrue(decodedWord.isEmpty());
    }

    @Test(groups = "decode")
    void When_DecodeWord_Expect_Success() {
        String decodedWord = decoder.decode("--./././-.-/..../..-/-.../");

        assertEquals(decodedWord, "geekhub");
    }

    @Test(groups = "decode")
    void When_DecodeText_Expect_Success() {
        String decodedWords = decoder.decode("--./././-.-/..../..-/-.../......./.--/---/.-./-../.../");

        assertEquals(decodedWords, "geekhub words");
    }

}