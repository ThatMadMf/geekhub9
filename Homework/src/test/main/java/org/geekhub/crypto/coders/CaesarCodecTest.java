package main.java.org.geekhub.crypto.coders;

import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CaesarCodecTest {

    private Encoder encoder;
    private Decoder decoder;

    @BeforeGroups("encode")
    public void initialiseEncode() {
        encoder = EncodersFactory.getEncoder("CAESAR");
    }

    @BeforeGroups("decode")
    public void initialiseDecode() {
        decoder = DecodersFactory.getDecoder("CAESAR");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "encode")
    public void encode_fails_when_input_is_null() {
        encoder.encode(null);
    }

    @Test(groups = "encode")
    void encode_of_empty_word_was_success() {
        String encodedWord = encoder.encode("");

        assertTrue(encodedWord.isEmpty());
    }

    @Test(groups = "encode")
    void encode_of_single_word_was_success() {
        String encodedWord = encoder.encode("geekhub");

        assertEquals(encodedWord, "vttzwjq");
    }

    @Test(groups = "encode")
    void encode_of_multiple_words_was_successs() {
        String encodedWords = encoder.encode("geekhub three words");

        assertEquals(encodedWords, "vttzwjq iwgtt ldgsh");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, groups = "decode")
    public void decode_fails_when_input_is_null() {
        decoder.decode(null);
    }

    @Test(groups = "decode")
    void decode_of_empty_word_was_success() {
        String decodedWord = decoder.decode("");

        assertTrue(decodedWord.isEmpty());
    }

    @Test(groups = "decode")
    void decode_of_single_word_was_success() {
        String decodedWord = decoder.decode("vttzwjq");

        assertEquals(decodedWord, "geekhub");
    }

    @Test(groups = "decode")
    void decode_of_multiple_words_was_successs() {
        String decodedWords = decoder.decode("vttzwjq iwgtt ldgsh");

        assertEquals(decodedWords, "geekhub three words");
    }
}