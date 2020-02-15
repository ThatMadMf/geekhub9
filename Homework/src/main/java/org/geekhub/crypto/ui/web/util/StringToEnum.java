package org.geekhub.crypto.ui.web.util;

import org.geekhub.crypto.coders.Algorithm;
import org.springframework.core.convert.converter.Converter;

public class StringToEnum implements Converter<String, Algorithm> {

    @Override
    public Algorithm convert(String source) {
        return Algorithm.valueOf(source);
    }
}
