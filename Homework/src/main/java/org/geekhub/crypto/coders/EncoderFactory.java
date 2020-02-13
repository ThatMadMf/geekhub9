package org.geekhub.crypto.coders;

import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EncoderFactory {

    private EnumMap<Algorithm, Encoder> encoders;

    public EncoderFactory(List<Encoder> encoders) {
        Map<Algorithm, Encoder> map = encoders.stream()
                .collect(Collectors.toMap(Encoder::getAlgorithm, Function.identity()));
        this.encoders = new EnumMap<>(map);
    }

    public Encoder getEncoder(Algorithm algorithm) {
        if(algorithm == null) {
            throw new CodecUnsupportedException("Invalid codec has been requested");
        }
        return encoders.get(algorithm);
    }
}
