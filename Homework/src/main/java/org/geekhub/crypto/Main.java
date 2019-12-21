package org.geekhub.crypto;

import org.geekhub.crypto.coders.ClassParser;
import org.geekhub.crypto.ui.MainMenu;

class Main {

    public static void main(String[] args) {
        ClassParser parser = new ClassParser();
        parser.getClasses();
        MainMenu menu = new MainMenu();
        menu.run();
    }
}
