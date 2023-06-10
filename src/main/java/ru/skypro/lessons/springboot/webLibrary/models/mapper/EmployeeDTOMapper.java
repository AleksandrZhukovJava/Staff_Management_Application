package ru.skypro.lessons.springboot.webLibrary.models.mapper;

import ru.skypro.lessons.springboot.webLibrary.domains.entity.Employee;
import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;


public class EmployeeDTOMapper {
    public static Employee toEmployee(EmployeeDTO employeeDTO){
        return Employee.builder()
                .id(employeeDTO.getId())
                .name(employeeDTO.getName())
                .salary(employeeDTO.getSalary())
                .position(PositionDTOMapper.toPosition(employeeDTO.getPosition()))
                .build();
    }
    public static EmployeeDTO fromEmployee(Employee employee){
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .salary(employee.getSalary())
                .position(PositionDTOMapper.fromPosition(employee.getPosition()))
                .build();
    }
}
