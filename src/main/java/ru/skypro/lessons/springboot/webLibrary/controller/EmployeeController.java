package ru.skypro.lessons.springboot.webLibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import ru.skypro.lessons.springboot.webLibrary.service.EmployeeService;
import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor

public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/salary/high-salary")
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
    @GetMapping("/salary/salaryHigherThan")
    public List<Employee> getEmployeesWithSalaryMoreThan(@RequestParam("salary") double compareSalary) {
        return employeeService.getEmployeesWithSalaryMoreThan(compareSalary);
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    @DeleteMapping("/{id}")
    public void removeEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }
    @PutMapping("/{id}")
    public void changeEmployeeById(@PathVariable Long id, @RequestBody Employee employee) {
        employeeService.changeEmployeeById(employee, id);
    }
    @PostMapping("/")
    public void createEmployees(@RequestBody List<Employee> listOfNewEmployee) {
        employeeService.createEmployees(listOfNewEmployee);
    }
}
