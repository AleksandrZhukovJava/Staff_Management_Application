package ru.skypro.lessons.springboot.webLibrary.service.report;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Report;
import ru.skypro.lessons.springboot.webLibrary.exceptions.customexceptions.IllegalIdException;
import ru.skypro.lessons.springboot.webLibrary.repository.ReportRepository;

import java.util.Optional;

import static constants.TestConstances.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepositoryMock;
    @InjectMocks
    private ReportServiceImpl out;

    @DisplayName("Add report and call repository twice")
    @Test
    @SneakyThrows
    void addReport_CorrectJson_ShouldCallRepositoryTwice() {
        when(reportRepositoryMock.getReportInfo())
                .thenReturn(REPORT_INFO_TEST_LIST);
        Report testReport = new Report(null, new ObjectMapper().writeValueAsString(REPORT_INFO_TEST_LIST).getBytes());
        when(reportRepositoryMock.save(eq(testReport)))
                .thenReturn(testReport);

        Integer actual = out.addReport();
        Integer expected = testReport.getId();

        assertEquals(expected,actual);

        verify(reportRepositoryMock, times(1)).getReportInfo();
        verify(reportRepositoryMock, times(1)).save(testReport);
    }

    @DisplayName("Return report by correct id and call repository once")
    @Test
    @SneakyThrows
    void getReportById_CorrectId_ShouldCallRepositoryOnce() {
        when(reportRepositoryMock.findById(CORRECT_ID))
                .thenReturn(Optional.of(REPORT_TEST));

        byte[] actual = out.getReportById(CORRECT_ID);

        assertArrayEquals(REPORT_TEST.getPositionReport(), actual);

        verify(reportRepositoryMock, times(1)).findById(CORRECT_ID);
    }
    @DisplayName("Throw exception if id was not correct and does not call repository")
    @Test
    @SneakyThrows
    void getReportById_NotCorrectId_ShouldCallRepositoryOnce() {
        assertThrows(IllegalIdException.class, () -> out.getReportById(INCORRECT_ID));
        verify(reportRepositoryMock, times(0)).findById(CORRECT_ID);
    }

}