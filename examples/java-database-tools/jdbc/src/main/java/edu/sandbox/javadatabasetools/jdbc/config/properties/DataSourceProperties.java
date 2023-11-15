package edu.sandbox.javadatabasetools.jdbc.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.datasource")
public record DataSourceProperties(String url, String username, String password) {
}
