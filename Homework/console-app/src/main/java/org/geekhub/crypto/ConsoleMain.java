package org.geekhub.crypto;

import org.geekhub.crypto.console.MainMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleMain implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConsoleMain.class, args);
    }

    private MainMenu mainMenu;

    public ConsoleMain(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public void run(String... args) throws Exception {
        mainMenu.run();
    }
}
