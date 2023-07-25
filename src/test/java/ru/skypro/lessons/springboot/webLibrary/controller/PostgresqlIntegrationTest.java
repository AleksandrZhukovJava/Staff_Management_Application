package ru.skypro.lessons.springboot.webLibrary.controller;


import config.PostgresqlDatabaseConnection;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PostgresqlDatabaseConnection.class)
public class PostgresqlIntegrationTest{
    @Autowired
    public PostgresqlDatabaseConnection postgresqlDatabaseConnection;
    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("Connection to temporary postgresql database container established")
    void testPostgresqlConnection() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            assertThat(conn).isNotNull();
        }
    }
}
