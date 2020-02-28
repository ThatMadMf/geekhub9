package org.geekhub.crypto.ui.web.configuration;

import org.geekhub.crypto.db.DataBasePopulator;
import org.geekhub.crypto.db.DataSource;
import org.geekhub.crypto.ui.web.iterceptor.RequestLogger;
import org.geekhub.crypto.ui.web.util.StringToAlgorithm;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToAlgorithm());
    }
}
