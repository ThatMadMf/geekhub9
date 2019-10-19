package org.geekhub.crypto.coders;

class VigenereCodec implements Encoder, Decoder {
    private static final String SHIFT_KEY = "keyword";
    private static final int ALPHABET_COUNT = 26;
    private static final int LOWERCASE_LEFT_BOUND = 97;
    private static final int LOWERCASE_RIGHT_BOUND = 122;
    private static final int UPPERCASE_LEFT_BOUND = 65;
    private static final int UPPERCASE_RIGHT_BOUND = 90;

    private char performRightShift(char c, int upperBorder, int lowerBorder, char keywordChar) {
        if (upperBorder == 90) {
            keywordChar = Character.toUpperCase(keywordChar);
        }
        int encoded = c + keywordChar - lowerBorder;
        if (encoded <= upperBorder) {
            return (char) (encoded);
        } else {
            return (char) (encoded + lowerBorder - upperBorder - 1);
        }
    }

    private char performLeftShift(char c, int lowerBorder, char keywordChar) {
        if (lowerBorder == 65) {
            keywordChar = Character.toUpperCase(keywordChar);
        }
        int shift = (c - keywordChar + ALPHABET_COUNT) % ALPHABET_COUNT;
        return (char) (shift + lowerBorder);
    }

    @Override
    public String encode(String input) {
        StringBuilder result = new StringBuilder();
        int keywordCount = 0;
        for (char symbol : input.toCharArray()) {
            if (symbol >= LOWERCASE_LEFT_BOUND && symbol <= LOWERCASE_RIGHT_BOUND) {
                result.append(performRightShift(symbol, LOWERCASE_RIGHT_BOUND, LOWERCASE_LEFT_BOUND, SHIFT_KEY.charAt(keywordCount)));
                keywordCount++;
            } else if (symbol >= UPPERCASE_LEFT_BOUND && symbol <= UPPERCASE_RIGHT_BOUND) {
                result.append(performRightShift(symbol, UPPERCASE_RIGHT_BOUND, UPPERCASE_LEFT_BOUND, SHIFT_KEY.charAt(keywordCount)));
                keywordCount++;
            } else {
                result.append(symbol);
            }
            if (keywordCount == SHIFT_KEY.length()) {
                keywordCount = 0;
            }
        }
        return result.toString();
    }

    @Override
    public String decode(String input) {
        StringBuilder result = new StringBuilder();
        int keywordCount = 0;
        for (char symbol : input.toCharArray()) {
            if (symbol >= LOWERCASE_LEFT_BOUND && symbol <= LOWERCASE_RIGHT_BOUND) {
                result.append(performLeftShift(symbol, LOWERCASE_LEFT_BOUND, SHIFT_KEY.charAt(keywordCount)));
                keywordCount++;
            } else if (symbol >= UPPERCASE_LEFT_BOUND && symbol <= UPPERCASE_RIGHT_BOUND) {
                result.append(performLeftShift(symbol, UPPERCASE_LEFT_BOUND, SHIFT_KEY.charAt(keywordCount)));
                keywordCount++;
            } else {
                result.append(symbol);
            }
            if (keywordCount == SHIFT_KEY.length()) {
                keywordCount = 0;
            }
        }
        return result.toString();
    }


}
