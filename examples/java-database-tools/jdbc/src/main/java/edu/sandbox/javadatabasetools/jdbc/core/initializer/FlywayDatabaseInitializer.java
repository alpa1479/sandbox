package edu.sandbox.javadatabasetools.jdbc.core.initializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class FlywayDatabaseInitializer {

    private final DataSource dataSource;

    @PostConstruct
    public void initialize() {
        var migration = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .cleanDisabled(false)
                .cleanOnValidationError(true)
                .load();
        migration.clean();
        migration.migrate();
    }
}
