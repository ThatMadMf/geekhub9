package org.geekhub.crypto.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan("org.geekhub.crypto.logging")
@PropertySource("classpath:config.properties")
public class LogConfig {

    @Value("${loggers}")
    private String[] destinations;

    @Bean
    public List<Logger> loggers() {
            return Arrays.stream(destinations)
                    .map(this::getDestination)
                    .collect(Collectors.toList());
    }

    private Logger getDestination(String destination) {
        LogDestination logDestination = LogDestination.valueOf(destination);

        switch (logDestination) {
            case CONSOLE:
                return new ConsoleLogger();
            case FILE:
                return new FileLogger();
            default:
                throw new IllegalArgumentException("Illegal logger");
        }
    }



}
