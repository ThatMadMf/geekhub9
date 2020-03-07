package org.geehub.crypto.coders;

public interface Decoder {

    String decode(String input);

    Algorithm getAlgorithm();
}
