package edu.sandbox.javadatabasetools.hibernate.core.initializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlywayDatabaseInitializer {

    private final Configuration configuration;

    @PostConstruct
    public void initialize() {
        var migration = Flyway.configure()
                .dataSource(
                        configuration.getProperty("hibernate.connection.url"),
                        configuration.getProperty("hibernate.connection.username"),
                        configuration.getProperty("hibernate.connection.password")
                )
                .locations("classpath:/db/migration")
                .cleanDisabled(false)
                .cleanOnValidationError(true)
                .load();
        migration.clean();
        migration.migrate();
    }
}
