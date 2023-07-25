package ru.skypro.lessons.springboot.webLibrary.controller;

import config.PostgresqlDatabaseConnection;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Import(PostgresqlDatabaseConnection.class)
public class InfoControllerIntegrationTest{
    @Autowired
    public PostgresqlDatabaseConnection postgresqlDatabaseConnection;
    @Autowired
    MockMvc mockMvc;
    @Value("${app.env}")
    private String environment;
    @Test
    void returnCorrectEnvironmentVariable() throws Exception {
        mockMvc.perform(get("/appInfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(environment));
    }
}