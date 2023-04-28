package ru.skypro.lessons.springboot.webLibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import ru.skypro.lessons.springboot.webLibrary.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/salary/{address}")
    public ResponseEntity<?> getMinMaxSalary(@PathVariable String address) {
        return switch (address) {
            case "min" -> ResponseEntity.ok().body(employeeService.getMinSalaryEmployee());
            case "max" -> ResponseEntity.ok().body(employeeService.getMaxSalaryEmployee());
            case "sum" -> ResponseEntity.ok().body(employeeService.getSalarySum());
            default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid address");
        };
    }
    @GetMapping("/high-salary")
    public List<Employee> getAboveAveragePaidEmployees() {
        return employeeService.getAboveAveragePaidEmployees();
    }
//    @GetMapping("/salary/max")
//    public Employee getMaxSalary() {
//        return employeeService.getMaxSalaryEmployee();
//    }
//    @GetMapping("/salary/min")
//    public Employee getMinSalary() {
//        return employeeService.getMinSalaryEmployee();
//    }
//    @GetMapping("/salary/sum")
//    public double getSumSalary() {
//        return employeeService.getSalarySum();
//    }
}
