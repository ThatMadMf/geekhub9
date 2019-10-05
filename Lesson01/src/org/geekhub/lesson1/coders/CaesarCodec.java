package org.geekhub.lesson1.coders;

import org.geekhub.lesson1.util.NotImplementedException;

import java.util.Calendar;

class CaesarCodec implements Encoder, Decoder {

    private int key;

    private void calcKey() {
        Calendar cal = Calendar.getInstance();
        key = 2024 - cal.get(Calendar.DAY_OF_MONTH) * cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.MONTH);
        key = (key % 26 == 0) ? 1 : (key % 26) ;
    }

    private void performRightShift(StringBuilder string, char c, int upperBorder) {
        string.append(c + key <= upperBorder ? (char)(c + key) : (char)(c + key - 26));
    }

    private void performLeftShift(StringBuilder string, char c, int lowerBorder) {
        string.append(c - key >= lowerBorder ? (char)(c - key) : (char)(c - key + 26));
    }

    @Override
    public String encode(String input) {
        calcKey();
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) >= 65 && input.charAt(i) <= 90) {
                performRightShift(result, input.charAt(i), 90);
            } else if (input.charAt(i) >= 97 && input.charAt(i) <= 122) {
                performRightShift(result, input.charAt(i), 122);
            } else {
                result.append(input.charAt(i));
            }
        }
        return  result.toString();
    }

    @Override
    public String decode(String input) {
        calcKey();
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) >= 65 && input.charAt(i) <= 90) {
                performLeftShift(result, input.charAt(i), 65);
            } else if (input.charAt(i) >= 97 && input.charAt(i) <= 122) {
                performLeftShift(result, input.charAt(i), 97);
            } else {
                result.append(input.charAt(i));
            }
        }
        return  result.toString();
    }

}
