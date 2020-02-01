package org.geekhub.crypto;

import org.geekhub.crypto.ui.console.MainMenu;
import org.geekhub.crypto.util.ApplicationContextWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.geekhub.crypto")
class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        ApplicationContextWrapper wrapper = new ApplicationContextWrapper();
        wrapper.setApplicationContext(context);
        context.getBean(MainMenu.class).run();
    }
}
