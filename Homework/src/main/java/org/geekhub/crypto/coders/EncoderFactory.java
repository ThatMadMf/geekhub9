package org.geekhub.crypto.coders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Set;

@Component
public class EncoderFactory {

    private EnumMap<Algorithm, Encoder> encoders;

    @Autowired
    public EncoderFactory(Set<Encoder> encoders) {
        this.encoders = new EnumMap<>(Algorithm.class);
        encoders.forEach(e -> this.encoders.put(e.getAlgorithm(), e));
    }

    public Encoder getEncoder(Algorithm algorithm) {
        return encoders.get(algorithm);
    }
}
