package org.geekhub.crypto.coders;

import org.geekhub.crypto.coders.codecs.UkrainianEnglish;
import org.geekhub.crypto.util.exception.IllegalInputException;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UkrainianEnglishTest {
    private Encoder encoder;
    private Decoder decoder;

    @BeforeGroups(groups = "encode")
    public void initialiseEncode() {
        encoder = new UkrainianEnglish();
    }

    @BeforeGroups(groups = "decode")
    public void initialiseDecode() {
        decoder = new UkrainianEnglish();
    }

    @Test(groups = "encode", expectedExceptions = IllegalInputException.class)
    void When_InputIsNull_Expect_Exception() {
        encoder.encode(null);
    }

    @Test(groups = "encode", expectedExceptions = IllegalInputException.class)
    void When_InputIsBlank_Expect_Exception() {
        encoder.encode("");
    }

    @Test(groups = "encode", expectedExceptions = IllegalInputException.class)
    void When_EncodeNotSupportedWord_Expect_Exception() {
        encoder.encode("#@$O@#$#@$@#$");
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

    @Test(groups = "encode")
    void When_EncodeWordOffline_Expect_Success() {
        Encoder offlineEncoder = new UkrainianEnglish();

        String encoded = offlineEncoder.encode("тест, слово! побачити?");

        assertEquals(encoded, "test, word! see?");
    }

    @Test(groups = "encode", expectedExceptions = IllegalInputException.class)
    void When_EncodeUnsupportedWordOffline_Expect_Exception() {
        Encoder offlineEncoder = new UkrainianEnglish("wrongKey");

        offlineEncoder.encode("notSupportedWord");
    }

    @Test(groups = "decode", expectedExceptions = IllegalInputException.class)
    void When_InputDecodeIsNull_Expect_Exception() {
        decoder.decode(null);
    }

    @Test(groups = "decode", expectedExceptions = IllegalInputException.class)
    void When_DecodeNotSupportedWord_Expect_Exception() {
        decoder.decode("#@$O@#$#@$@#$");
    }

    @Test(groups = "decode", expectedExceptions = IllegalInputException.class)
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

        assertEquals(decoded, "тестове слово див");
    }


    @Test(groups = "decode")
    void When_DecodeTextOffline_Expect_Success() {
        Decoder offlineDecoder = new UkrainianEnglish();

        String decoded = offlineDecoder.decode("test word see");

        assertEquals(decoded, "тестове слово див");
    }

    @Test(groups = "decode", expectedExceptions = IllegalInputException.class)
    void When_DecodeUnsupportedWordOffline_Expect_Exception() {
        Decoder offlineDecoder = new UkrainianEnglish("wrongRey");

        offlineDecoder.decode("notSupportedWord");
    }
}