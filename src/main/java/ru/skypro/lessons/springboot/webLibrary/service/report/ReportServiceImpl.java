package ru.skypro.lessons.springboot.webLibrary.service.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Position;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Report;
import ru.skypro.lessons.springboot.webLibrary.domains.pojo.Statistic;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.webLibrary.repository.PositionRepository;
import ru.skypro.lessons.springboot.webLibrary.repository.ReportRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.skypro.lessons.springboot.webLibrary.utility.Validation.modelValidation;

@Service
@Transactional
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final ReportRepository reportRepository;

    @Override
    public Integer addReport() throws IOException {
        List<Statistic> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Position position : positionRepository.findAll()) {
            Statistic statistic = new Statistic();
            statistic.setPositionName(position.getName());
            statistic.setCount(employeeRepository.findAllByPositionIdCount(position.getId()));
            statistic.setMaxSalary(employeeRepository.returnMaxSalaryByPosition(position.getId()));
            statistic.setMinSalary(employeeRepository.returnMinSalaryByPosition(position.getId()));
            statistic.setAvgSalary(employeeRepository.returnAvgSalaryByPosition(position.getId()));
            list.add(statistic);
        }
        return reportRepository.save(new Report(null, objectMapper.writeValueAsString(list).getBytes())).getId();
    }

    @Override
    public byte[] getReportById(Integer id){
        modelValidation(id);
        return reportRepository.findById(id).orElseThrow(IllegalArgumentException::new).getPositionReport();
    }
}
