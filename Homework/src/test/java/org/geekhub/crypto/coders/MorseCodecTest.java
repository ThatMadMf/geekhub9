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
    void encode_fails_when_input_is_null() {
        encoder.encode(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "encode")
    void encode_fails_when_input_not_in_single_case() {
        encoder.encode("WOrd");
    }

    @Test(groups = "encode")
    void encode_of_empty_word_was_success() {
        String encodedWord = encoder.encode("");

        assertTrue(encodedWord.isEmpty());
    }

    @Test(groups = "encode")
    void encode_of_single_word_was_success() {
        String encodedWord = encoder.encode("geekhub");

        assertEquals(encodedWord, "--./././-.-/..../..-/-.../");
    }

    @Test(groups = "encode")
    void encode_of_multiple_words_was_successs() {
        String encodedWords = encoder.encode("geekhub words");

        assertEquals(encodedWords, "--./././-.-/..../..-/-.../......./.--/---/.-./-../.../");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "decode")
    void decode_fails_when_input_is_null() {
        decoder.decode(null);
    }

    @Test(groups = "decode")
    void decode_of_empty_word_was_success() {
        String decodedWord = decoder.decode("");

        assertTrue(decodedWord.isEmpty());
    }

    @Test(groups = "decode")
    void decode_of_single_word_was_success() {
        String decodedWord = decoder.decode("--./././-.-/..../..-/-.../");

        assertEquals(decodedWord, "geekhub");
    }

    @Test(groups = "decode")
    void decode_of_multiple_words_was_successs() {
        String decodedWords = decoder.decode("--./././-.-/..../..-/-.../......./.--/---/.-./-../.../");

        assertEquals(decodedWords, "geekhub words");
    }

}