package org.geekhub.crypto.ui.web.configuration;

import org.geekhub.crypto.db.DataBasePopulator;
import org.geekhub.crypto.db.DataSource;
import org.geekhub.crypto.ui.web.iterceptor.RequestLogger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan("org.geekhub.crypto.ui.web.controller")
public class WebConfig implements WebMvcConfigurer {

    private RequestLogger requestLogger;

    public WebConfig(RequestLogger requestLogger, DataBasePopulator populator, DataSource dataSource) {
        this.requestLogger = requestLogger;
        populator.createTable(dataSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogger);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
