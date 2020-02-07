package org.geekhub.crypto.coders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DecoderFactory {

    private EnumMap<Algorithm, Decoder> decoders;

    @Autowired
    public DecoderFactory(List<Decoder> decoders) {
        Map<Algorithm, Decoder> map = decoders.stream()
                .collect(Collectors.toMap(Decoder::getAlgorithm, Function.identity()));
        this.decoders = new EnumMap<>(map);
    }

    public Decoder getDecoder(Algorithm algorithm) {
        return decoders.get(algorithm);
    }
}