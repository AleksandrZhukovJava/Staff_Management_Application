package ru.skypro.lessons.springboot.webLibrary.service;

import ru.skypro.lessons.springboot.webLibrary.model.Employee;

import java.util.List;

public interface EmployeeService {
    double getSalarySum();
    List<Employee> getMinSalaryEmployee();
    List<Employee> getMaxSalaryEmployee();
    List<Employee> getAboveAveragePaidEmployees();
}
