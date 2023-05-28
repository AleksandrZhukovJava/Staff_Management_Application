package ru.skypro.lessons.springboot.webLibrary.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Report;
import ru.skypro.lessons.springboot.webLibrary.models.projections.ReportInfo;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report,Integer> {
    @Query(value = "SELECT new ru.skypro.lessons.springboot.webLibrary.models.projections.ReportInfo (p.name, count(p.name), max(e.salary), min(e.salary), avg(e.salary)) " +
            "FROM Employee e JOIN FETCH Position p on e.position = p GROUP BY p.name")
    List<ReportInfo> getReportInfo();
}
