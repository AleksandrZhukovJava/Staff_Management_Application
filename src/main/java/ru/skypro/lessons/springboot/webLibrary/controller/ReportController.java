package ru.skypro.lessons.springboot.webLibrary.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.webLibrary.service.report.ReportService;

import java.io.IOException;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
@Tag(name = "Report data base actions")

public class ReportController {
    private final ReportService reportService;
    @PostMapping
    public Integer addReport() throws IOException {
        return reportService.addReport();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getEmployeeById(@PathVariable Integer id) {
        String fileName = "report.json";
        Resource resource = new ByteArrayResource(reportService.getReportById(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }
}
