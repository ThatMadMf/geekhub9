package org.geekhub.crypto.coders;

class MorseCodec implements Encoder, Decoder {

    private static final char[] alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '0'};
    private static final String[] codes = new String[]{".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
            ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
            "-.--", "--..", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----"};

    private String encodeCharacter(char c) {
        return codes[new String(alphabet).indexOf(c)];
    }

    private char decodeCharacter(String string) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i].equals(string)) {
                return alphabet[i];
            }
        }
        throw new IllegalArgumentException("Cannot find character");
    }

    private String encodeWord(String input) {
        StringBuilder codedWord = new StringBuilder();
        char[] word = input.toCharArray();

        for (int j = 0; j < word.length; j++) {
            if (j != 0) {
                codedWord.append(" ");
            }
            codedWord.append(encodeCharacter(word[j]));
        }
        return codedWord.toString();
    }

    private String decodedWord(String input) {
        StringBuilder decodedWord = new StringBuilder();
        String[] codedChars = input.split("\\s+");

        for (String codedChar : codedChars) {
            decodedWord.append(decodeCharacter(codedChar));
        }

        return decodedWord.toString();
    }

    @Override
    public String encode(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                System.out.println("Error. You have to use characters of single case.");
                System.exit(1);
            }
        }

        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(encodeWord(word));
            result.append(" / ");
        }

        result.setLength(result.length() - 3);
        return result.toString();
    }

    @Override
    public String decode(String input) {
        String[] words = input.split("(\\s+\\/\\s)");
        StringBuilder result = new StringBuilder();

        for (String str : words) {
            result.append(decodedWord(str));
            result.append(" ");
        }

        result.setLength(result.length() - 1);
        return result.toString().toLowerCase();
    }
}
