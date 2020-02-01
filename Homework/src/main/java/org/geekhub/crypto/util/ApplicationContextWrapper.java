package org.geekhub.crypto.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.geekhub.crypto")
public class ApplicationContextWrapper implements ApplicationContextAware {

    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static<T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static<T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

}
