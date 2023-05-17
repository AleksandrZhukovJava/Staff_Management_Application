package ru.skypro.lessons.springboot.webLibrary.service;

import ru.skypro.lessons.springboot.webLibrary.model.Employee;

import java.util.List;

public interface EmployeeService {
    double getSalarySum();
    List<Employee> getMinSalaryEmployee();
    List<Employee> getMaxSalaryEmployee();
    List<Employee> getAboveAveragePaidEmployees();
    List<Employee> getEmployeesWithSalaryMoreThan(double salary);
    Employee getEmployeeById(Long id);
    void deleteEmployeeById(Long id);
    void changeEmployeeById(Employee employee, Long id);
    void createEmployees(List<Employee> listOfNewEmployee);
}
