package ru.skypro.lessons.springboot.webLibrary.controller;

import config.PostgresqlDatabaseConnection;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Report;
import ru.skypro.lessons.springboot.webLibrary.repository.ReportRepository;

import java.util.List;

import static constants.TestConstances.INCORRECT_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN", "USER"})
@Transactional
@Import(PostgresqlDatabaseConnection.class)
public class ReportControllerIntegrationTest{
    @Autowired
    public PostgresqlDatabaseConnection postgresqlDatabaseConnection;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ReportRepository reportRepository;
    @Test
    void saveReportInDatabase_thenReturnReportInfoNotEmpty() throws Exception {
        mockMvc.perform(post("/admin/report"))
                .andExpect(status().isOk());
        List<Report> actual = reportRepository.findAll();

        assertThat(actual).isNotEmpty();

        MvcResult result = mockMvc.perform(get("/admin/report/" + actual.get(0).getId()))
                .andExpect(status().isOk())
                .andReturn();

        byte[] resourceContent = result.getResponse().getContentAsByteArray();
        assertThat(resourceContent).isNotEmpty();
    }
    @Test
    void givenIncorrectId_thenReturn400Status() throws Exception {
        mockMvc.perform(get("/admin/report/" + INCORRECT_ID))
                .andExpect(status().is4xxClientError());
    }
}