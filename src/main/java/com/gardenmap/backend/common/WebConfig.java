package com.gardenmap.backend.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS configuration for the API.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    public WebConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = corsProperties.getAllowedOrigins().toArray(String[]::new);

        registry.addMapping("/api/**")
                .allowedOrigins(origins)
                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
