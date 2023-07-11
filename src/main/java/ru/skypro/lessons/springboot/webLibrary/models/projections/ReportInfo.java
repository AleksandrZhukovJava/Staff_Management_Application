package ru.skypro.lessons.springboot.webLibrary.models.projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportInfo implements Serializable {
    private String positionName;
    private Long count;
    private double maxSalary;
    private double minSalary;
    private double avgSalary;
}
