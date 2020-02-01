package org.geekhub.crypto.coders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Set;

@Component
public class DecoderFactory {

    private EnumMap<Algorithm, Decoder> decoders;

    @Autowired
    public DecoderFactory(Set<Decoder> decoders) {
        this.decoders = new EnumMap<>(Algorithm.class);
        decoders.forEach(d -> this.decoders.put(d.getAlgorithm(), d));
    }

    public Decoder getDecoder(Algorithm algorithm) {
        return decoders.get(algorithm);
    }
}