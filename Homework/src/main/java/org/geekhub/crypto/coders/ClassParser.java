package org.geekhub.crypto.coders;

import org.geekhub.crypto.annotations.Codec;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

public class ClassParser {

    static final List<Class<?>> matchingClasses = getClasses();

    private static List<Class<?>> getClasses() {
        Reflections reflections = new Reflections("org.geekhub.crypto");
        return new ArrayList<>(reflections.getTypesAnnotatedWith(Codec.class));
    }
}