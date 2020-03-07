package org.geehub.crypto.coders;

public interface Encoder {

    String encode(String input);

    Algorithm getAlgorithm();
}
