package org.geekhub.crypto.web.configuration;

import org.geekhub.crypto.web.iterceptor.PasswordInterceptor;
import org.geekhub.crypto.web.iterceptor.RequestLogger;
import org.geekhub.crypto.web.util.StringToAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestLogger requestLogger;
    private PasswordInterceptor passwordInterceptor;

    public WebConfig(RequestLogger requestLogger, PasswordInterceptor passwordInterceptor) {
        this.requestLogger = requestLogger;
        this.passwordInterceptor = passwordInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogger);
        registry.addInterceptor(passwordInterceptor);
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
