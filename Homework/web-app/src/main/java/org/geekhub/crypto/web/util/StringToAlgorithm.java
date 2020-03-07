package org.geekhub.crypto.web.util;

import org.geekhub.crypto.coders.Algorithm;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.springframework.core.convert.converter.Converter;

public class StringToAlgorithm implements Converter<String, Algorithm> {

    @Override
    public Algorithm convert(String source) {
        try {
            return Algorithm.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CodecUnsupportedException("Unsupported codec", source);
        }
    }
}
