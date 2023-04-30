package ru.skypro.lessons.springboot.webLibrary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public double getSalarySum() {
        return employeeRepository.returnAllEmployee().stream().map(Employee::getSalary).reduce(0.0, Double::sum);
    }

    @Override
    public List<Employee> getMinSalaryEmployee() {
        return employeeRepository.returnAllEmployee()
                .stream()
                .filter(x -> x.getSalary() == employeeRepository.returnAllEmployee()
                        .stream()
                        .min(Comparator.comparingDouble(Employee::getSalary))
                        .map(Employee::getSalary).orElse(-1.0)).collect(Collectors.toList());
    }

    @Override
    public List<Employee> getMaxSalaryEmployee() {
        return employeeRepository.returnAllEmployee()
                .stream()
                .filter(x -> x.getSalary() == employeeRepository.returnAllEmployee()
                        .stream()
                        .max(Comparator.comparingDouble(Employee::getSalary))
                        .map(Employee::getSalary).orElse(-1.0)).collect(Collectors.toList());
    }

    @Override
    public List<Employee> getAboveAveragePaidEmployees() {
        return employeeRepository.returnAllEmployee().stream().filter(x -> x.getSalary() > getSalarySum() / employeeRepository.returnAllEmployee().size()).collect(Collectors.toList());
    }
}
