package org.geekhub.crypto.coders;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

public class MapReverser {

    public static <V, K> Map<V, K> reverse(Map<K, V> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(Entry::getValue, Entry::getKey));
    }
}
