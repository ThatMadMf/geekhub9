package org.geekhub.crypto.util;

public class ConsoleLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println(message);
    }

    @Override
    public void warn(String message) {
        System.out.println("WARNING:" + message);
    }

    @Override
    public void error(String message) {
        System.out.println("ERROR: " + message);
    }
}
