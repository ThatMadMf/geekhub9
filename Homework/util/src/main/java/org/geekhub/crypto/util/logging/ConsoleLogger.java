package org.geekhub.crypto.util.logging;

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

    @Override
    public void error(Exception e) {
        String logDivider = "####################";
        String partDivider = "\n";
        System.out.println(logDivider);
        System.out.println("ERROR:" + e.getMessage() + partDivider);
        System.out.println(e.getCause() + partDivider);
    }
}
