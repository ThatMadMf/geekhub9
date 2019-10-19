package org.geekhub.lesson1.coders;

class CaesarCodec implements Encoder, Decoder {

    private static final int SHIFT_KEY = 15;
    private static final int ALPHABET_COUNT = 26;
    private static final int LOWERCASE_LEFT_BOUND = 65;
    private static final int LOWERCASE_RIGHT_BOUND = 90;
    private static final int UPPERCASE_LEFT_BOUND = 97;
    private static final int UPPERCASE_RIGHT_BOUND = 122;

    private char performRightShift(char c, int upperBorder) {
        return c + SHIFT_KEY <= upperBorder ? (char) (c + SHIFT_KEY) : (char) (c + SHIFT_KEY - ALPHABET_COUNT);
    }

    private char performLeftShift(char c, int lowerBorder) {
        return c - SHIFT_KEY >= lowerBorder ? (char) (c - SHIFT_KEY) : (char) (c - SHIFT_KEY + ALPHABET_COUNT);
    }

    @Override
    public String encode(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= LOWERCASE_LEFT_BOUND && input.charAt(i) <= LOWERCASE_RIGHT_BOUND) {
                result.append(performRightShift(input.charAt(i), LOWERCASE_RIGHT_BOUND));
            } else if (input.charAt(i) >= UPPERCASE_LEFT_BOUND && input.charAt(i) <= UPPERCASE_RIGHT_BOUND) {
                result.append(performRightShift(input.charAt(i), UPPERCASE_RIGHT_BOUND));
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
            if (input.charAt(i) >= LOWERCASE_LEFT_BOUND && input.charAt(i) <= LOWERCASE_RIGHT_BOUND) {
                result.append(performLeftShift(input.charAt(i), LOWERCASE_LEFT_BOUND));
            } else if (input.charAt(i) >= UPPERCASE_LEFT_BOUND && input.charAt(i) <= UPPERCASE_RIGHT_BOUND) {
                result.append(performLeftShift(input.charAt(i), UPPERCASE_LEFT_BOUND));
            } else {
                result.append(input.charAt(i));
            }
        }
        return result.toString();
    }

}
