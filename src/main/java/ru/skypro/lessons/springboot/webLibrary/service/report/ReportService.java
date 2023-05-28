package ru.skypro.lessons.springboot.webLibrary.service.report;

import java.io.IOException;

public interface ReportService {
    Integer addReport() throws IOException;

    byte[] getReportById(Integer id);
}
