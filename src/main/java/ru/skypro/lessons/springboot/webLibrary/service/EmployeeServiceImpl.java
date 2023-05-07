package ru.skypro.lessons.springboot.webLibrary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;


    @Override
    public double getSalarySum() {
        return employeeRepository.returnSumSalary();
    }

    @Override
    public List<Employee> getMinSalaryEmployee() {
        return employeeRepository.returnMinSalaryEmployees();
    }

    @Override
    public List<Employee> getMaxSalaryEmployee() {
        return employeeRepository.returnMaxSalaryEmployees();
    }

    @Override
    public List<Employee> getAboveAveragePaidEmployees() {
        return employeeRepository.getAboveAveragePaidEmployees();
    }

}
