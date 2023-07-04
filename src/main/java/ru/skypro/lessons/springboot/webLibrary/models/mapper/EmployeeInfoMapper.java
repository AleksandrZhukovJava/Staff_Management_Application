package ru.skypro.lessons.springboot.webLibrary.models.mapper;

import ru.skypro.lessons.springboot.webLibrary.domains.entity.Employee;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Position;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;

public class EmployeeInfoMapper {
    public static EmployeesInfo fromEmployee(Employee employees) {
        return new EmployeesInfo(employees.getName(),
                employees.getSalary(),
                employees.getPosition().getName());
    }
    public static Employee toEmployee(EmployeesInfo employeesInfo) {
        return Employee.builder()
                .id(null)
                .name(employeesInfo.getName())
                .salary(employeesInfo.getSalary())
                .position(Position.builder()
                        .id(null)
                        .name(employeesInfo.getPositionName())
                        .build())
                .build();
    }
}
