package org.geekhub.crypto.coders;

import org.testng.annotations.Test;

public class DecodersFactoryTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNull_ThrowingException() {
        DecodersFactory.getDecoder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsEmpty_ThrowingException() {
        DecodersFactory.getDecoder("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNotExistingElement_ThrowingException() {
        DecodersFactory.getDecoder("NotExistingAlgorithm");
    }

}