package org.geekhub.crypto.coders;

import org.geekhub.crypto.coders.codecs.MorseCodec;
import org.geekhub.crypto.exception.IllegalInputException;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MorseCodecTest {
    private Encoder encoder;
    private Decoder decoder;

    @BeforeGroups(groups = "encode")
    public void initialiseEncode() {
        encoder = new MorseCodec();
    }

    @BeforeGroups(groups = "decode")
    public void initialiseDecode() {
        decoder = new MorseCodec();
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "encode")
    void When_InputIsNull_Expect_EncodeFail() {
        encoder.encode(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "encode")
    void When_InputNotInSingleCase_Expect_EncodeFail() {
        encoder.encode("WOrd");
    }

    @Test(groups = "encode", expectedExceptions = IllegalArgumentException.class)
    void When_EncodeEmptyWord_Expect_Success() {
        encoder.encode("");
    }

    @Test(groups = "encode", expectedExceptions = IllegalInputException.class)
    void When_EncodeNotSupportedCharacter_Expect_Exception() {
        encoder.encode("*un_supported%");
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

    @Test(groups = "decode", expectedExceptions = IllegalArgumentException.class)
    void When_DecodeEmptyWord_Expect_Success() {
        decoder.decode("");
    }

    @Test(groups = "decode", expectedExceptions = IllegalInputException.class)
    void When_DecodeNotSupportedCharacter_Expect_Exception() {
        decoder.decode("*un_supported%");
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