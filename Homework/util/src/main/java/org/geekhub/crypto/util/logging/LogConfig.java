package org.geekhub.crypto.util.logging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
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


    @Bean
    public CompositeLogger getLogger(List<Logger> loggers) {
        return new CompositeLogger(loggers);
    }

}
