package ru.skypro.lessons.springboot.webLibrary.service.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Report;
import ru.skypro.lessons.springboot.webLibrary.repository.ReportRepository;

import java.io.IOException;

import static ru.skypro.lessons.springboot.webLibrary.utility.Validation.modelValidation;

@Service
@Transactional
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Override
    public Integer addReport() throws IOException {
        return reportRepository.save(new Report(null, new ObjectMapper().writeValueAsString(reportRepository.getReportInfo()).getBytes())).getId();
    }

    @Override
    public byte[] getReportById(Integer id){
        modelValidation(id);
        return reportRepository.findById(id).orElseThrow(IllegalArgumentException::new).getPositionReport();
    }
}
