package org.geekhub.crypto.coders;

import org.testng.annotations.Test;

public class DecodersFactoryTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNull_Expect_Exception() {
        DecodersFactory.getDecoder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsEmpty_Expect_Exception() {
        DecodersFactory.getDecoder("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    void When_InputIsNotExistingElement_Expect_Exception() {
        DecodersFactory.getDecoder("NotExistingAlgorithm");
    }

}