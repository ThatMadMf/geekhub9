package org.geekhub.crypto.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("org.geekhub.crypto.logging")
@PropertySource("classpath:config.properties")
public class LogConfig {

    @Value("${loggers}")
    private String[] destinations;

    @Bean
    public List<Logger> loggers() {
        return new ArrayList<>(List.of(new ConsoleLogger()));
    }

}
