package org.geekhub.crypto.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextWrapper implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    public void setApplicationContext(ApplicationContext context) {
        CONTEXT = context;
    }

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }

    public static<T> T getBean(Class<T> clazz) {
        return CONTEXT.getBean(clazz);
    }

    public static<T> T getBean(String name, Class<T> clazz) {
        return CONTEXT.getBean(name, clazz);
    }

}
