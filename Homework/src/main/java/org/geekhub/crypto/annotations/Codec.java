package org.geekhub.crypto.annotations;

import org.geekhub.crypto.coders.Algorithm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Codec {
    Algorithm algorithm();
}
