package org.geekhub.crypto.coders;

import org.testng.annotations.Test;

public class EncodersFactoryTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNull_ThrowingException() {
        EncodersFactory.getEncoder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNotExistingElement_ThrowingException() {
        EncodersFactory.getEncoder(Algorithm.valueOf("NotExistingAlgorithm"));
    }

}