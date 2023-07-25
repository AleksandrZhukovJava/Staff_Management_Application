package config;

import lombok.AllArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
public class PostgresqlDatabaseConnection extends PostgreSQLContainer<PostgresqlDatabaseConnection>{
    private static final String IMAGE_VERSION = "postgres:alpine";
    private static PostgresqlDatabaseConnection container;
    @Override
    public void start() {
        super.start();
    }
    @Override
    public void stop() {
    }
}
