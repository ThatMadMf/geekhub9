package org.geekhub.crypto.coders;

import org.geekhub.crypto.annotations.Key;
import org.geekhub.crypto.annotations.Shift;
import org.geekhub.crypto.exception.CodecUnsupportedException;
import org.geekhub.crypto.exception.IllegalAnnotaionException;
import org.geekhub.crypto.util.PropertiesReader;


import java.lang.reflect.Field;

public class FieldInitialiser {

    public static Object initialiseFields(Object instance) {
        try {
            Field[] fields = instance.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Shift.class)) {
                    if (instance.getClass() == CaesarCodec.class) {
                        field.setAccessible(true);
                        PropertiesReader propertiesReader = new PropertiesReader("config.properties");
                        field.set(instance, Integer.parseInt(propertiesReader.readSingleProperty("shift")));
                    } else {
                        throw new IllegalAnnotaionException("Attempt to set shift to " + instance.getClass());
                    }
                }
                if (field.isAnnotationPresent(Key.class)) {
                    if (instance.getClass() == VigenereCodec.class) {
                        PropertiesReader propertiesReader = new PropertiesReader("config.properties");
                        field.setAccessible(true);
                        field.set(instance, propertiesReader.readSingleProperty("key"));
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
