package org.geekhub.crypto.coders;

import org.geekhub.crypto.annotations.Key;
import org.geekhub.crypto.annotations.Shift;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.exception.IllegalAnnotaionException;

import java.lang.reflect.Field;

public class FieldInitialiser {

    public static Object initialiseFields(Object instance) {
        try {
            Field[] fields = instance.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Shift.class)) {
                    if (instance.getClass() == CaesarCodec.class) {
                        Shift shift = field.getAnnotation(Shift.class);
                        field.setAccessible(true);
                        field.set(instance, shift.shift());
                    } else {
                        throw new IllegalAnnotaionException("Attempt to set shift to " + instance.getClass());
                    }
                }
                if (field.isAnnotationPresent(Key.class)) {
                    if (instance.getClass() == VigenereCodec.class) {
                        Key key = field.getAnnotation(Key.class);
                        field.setAccessible(true);
                        field.set(instance, key.keyword());
                    } else {
                        throw new IllegalAnnotaionException("Attempt to set key to " + instance.getClass());
                    }
                }
            }
            return instance;
        } catch (IllegalAccessException e) {
            throw new CodecUnsupportedException("Cannot create such a coder");
        }
    }
}
