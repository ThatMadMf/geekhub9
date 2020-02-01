package org.geekhub.crypto.ui.web;

import org.geekhub.crypto.ui.web.iterceptor.RequestLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public RequestLogger getRequestLogger() {
        return new RequestLogger();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRequestLogger());
    }
}
