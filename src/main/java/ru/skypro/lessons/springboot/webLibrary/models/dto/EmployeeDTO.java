package ru.skypro.lessons.springboot.webLibrary.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Employee;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO implements Serializable {
    private Integer id;
    private String name;
    private double salary;
    private PositionDTO position;

    public static EmployeeDTO fromEmployee(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .salary(employee.getSalary())
                .name(employee.getName())
                .position(PositionDTO.fromPosition(employee.getPosition()))
                .build();

    }

    public Employee toEmployee() {
        return new Employee(this.id, this.salary, this.name, this.position.toPosition());
    }

}
