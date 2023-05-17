package ru.skypro.lessons.springboot.webLibrary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.webLibrary.utility.ModelValidation;

import java.util.List;

import static ru.skypro.lessons.springboot.webLibrary.utility.ModelValidation.modelValidation;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
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
    @Override
    public List<Employee> getEmployeesWithSalaryMoreThan(double salary) throws IllegalArgumentException{
        modelValidation(salary);
        return employeeRepository.getEmployeesWithSalaryMoreThan(salary);
    }
    @Override
    public Employee getEmployeeById(int id) throws IllegalArgumentException{
        modelValidation(id);
        return employeeRepository.getEmployeeById(id);
    }
    @Override
    public void deleteEmployeeById(int id) throws IllegalArgumentException{
        modelValidation(id);
        employeeRepository.deleteEmployeeById(id);
    }
    @Override
    public void changeEmployeeById(Employee employee, int id) throws IllegalArgumentException{
        modelValidation(employee);
        employeeRepository.changeEmployeeById(employee.getId(), employee.getName(), employee.getSalary(), id);
    }
    @Override
    public void createEmployees(List<Employee> listOfNewEmployee) throws IllegalArgumentException{
        listOfNewEmployee.stream().forEach(ModelValidation::modelValidation);
            employeeRepository.saveAll(listOfNewEmployee);
    }
}
