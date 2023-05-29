package ru.skypro.lessons.springboot.webLibrary.models.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportInfo {
    private String positionName;
    private Long count;
    private double maxSalary;
    private double minSalary;
    private double avgSalary;
}
