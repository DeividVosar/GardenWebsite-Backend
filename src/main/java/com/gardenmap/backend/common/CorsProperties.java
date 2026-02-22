package com.gardenmap.backend.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Binds CORS settings from configuration.
 */
@Component
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {

    private List<String> allowedOrigins = new ArrayList<>();

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
