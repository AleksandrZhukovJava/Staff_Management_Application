package ru.skypro.lessons.springboot.webLibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import ru.skypro.lessons.springboot.webLibrary.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/high-salary")
    public List<Employee> getAboveAveragePaidEmployees() {
        return employeeService.getAboveAveragePaidEmployees();
    }
    @GetMapping("/salary/max")
    public List<Employee> getMaxSalary() {
        return employeeService.getMaxSalaryEmployee();
    }
    @GetMapping("/salary/min")
    public List<Employee> getMinSalary() {
        return employeeService.getMinSalaryEmployee();
    }
    @GetMapping("/salary/sum")
    public double getSumSalary() {
        return employeeService.getSalarySum();
    }
}
