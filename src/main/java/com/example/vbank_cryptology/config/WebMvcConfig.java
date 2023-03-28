package com.example.vbank_cryptology.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://10.144.78.44:8080")
                .allowedOriginPatterns("http://10.144.254.182:8080")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
