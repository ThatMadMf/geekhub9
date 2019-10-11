package org.geekhub.lesson1.coders;

class CaesarCodec implements Encoder, Decoder {

    private static final int key = 15;
    private static final int lowerLeftBorder = 65;
    private static final int lowerRightBorder = 90;
    private static final int upperLeftBorder = 97;
    private static final int upperRightBorder = 122;

    private char performRightShift(char c, int upperBorder) {
        return c + key <= upperBorder ? (char) (c + key) : (char) (c + key - 26);
    }

    private char performLeftShift(char c, int lowerBorder) {
        return c - key >= lowerBorder ? (char) (c - key) : (char) (c - key + 26);
    }

    @Override
    public String encode(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= lowerLeftBorder && input.charAt(i) <= lowerRightBorder) {
                result.append(performRightShift(input.charAt(i), lowerRightBorder));
            } else if (input.charAt(i) >= upperLeftBorder && input.charAt(i) <= upperRightBorder) {
                result.append(performRightShift(input.charAt(i), upperRightBorder));
            } else {
                result.append(input.charAt(i));
            }
        }
        return result.toString();
    }

    @Override
    public String decode(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= lowerLeftBorder && input.charAt(i) <= lowerRightBorder) {
                result.append(performLeftShift(input.charAt(i), lowerLeftBorder));
            } else if (input.charAt(i) >= upperLeftBorder && input.charAt(i) <= upperRightBorder) {
                result.append(performLeftShift(input.charAt(i), upperLeftBorder));
            } else {
                result.append(input.charAt(i));
            }
        }
        return result.toString();
    }

}
