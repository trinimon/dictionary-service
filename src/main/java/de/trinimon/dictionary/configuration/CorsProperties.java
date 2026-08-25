package de.trinimon.dictionary.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "de.trinimon.cors")
public record CorsProperties(
        boolean enabled,
        List<String> allowedOrigins) {
}