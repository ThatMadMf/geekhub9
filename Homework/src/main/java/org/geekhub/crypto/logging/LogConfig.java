package org.geekhub.crypto.logging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@ComponentScan("org.geekhub.crypto.logging")
@PropertySource("classpath:config.properties")
public class LogConfig {

    @Bean
    @ConditionalOnProperty(value = "enable.console.logger", havingValue = "true", matchIfMissing = true)
    public ConsoleLogger consoleLogger() {
        return new ConsoleLogger();
    }

    @Bean
    @ConditionalOnProperty(value = "enable.file.logger", havingValue = "true", matchIfMissing = true)
    public FileLogger fileLogger() {
        return new FileLogger();
    }

}
