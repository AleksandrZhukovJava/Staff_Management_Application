package config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlDatabaseConnection extends PostgreSQLContainer<PostgresqlDatabaseConnection>{
    private static final String IMAGE_VERSION = "postgres:alpine";
    private static PostgresqlDatabaseConnection container;
    private PostgresqlDatabaseConnection() {
        super(IMAGE_VERSION);
    }
    public static PostgresqlDatabaseConnection getInstance() {
        if (container == null) {
            container = new PostgresqlDatabaseConnection();
        }
        return container;
    }
    @Override
    public void start() {
        super.start();
    }
    @Override
    public void stop() {
    }
}
