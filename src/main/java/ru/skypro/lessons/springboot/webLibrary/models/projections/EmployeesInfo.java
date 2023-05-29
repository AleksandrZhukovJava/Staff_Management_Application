package ru.skypro.lessons.springboot.webLibrary.models.projections;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeesInfo {
    private String name;
    private double salary;
    private String positionName;

}

