package ru.skypro.lessons.springboot.webLibrary.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;
import ru.skypro.lessons.springboot.webLibrary.service.employee.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/user/employee")
@AllArgsConstructor
@Tag(name = "Employees data base")
public class EmployeeUserController {
    private final EmployeeService employeeService;

    @GetMapping("/salary/high-salary")
    public List<EmployeeDTO> getAboveAveragePaidEmployees() {
        return employeeService.getAboveAveragePaidEmployees();
    }

    @GetMapping("/withHighestSalary")
    public List<EmployeeDTO> getMaxSalary() {
        return employeeService.getMaxSalaryEmployee();
    }

    @GetMapping("/salary/min")
    public List<EmployeeDTO> getMinSalary() {
        return employeeService.getMinSalaryEmployee();
    }

    @GetMapping("/salary/sum")
    public double getSumSalary() {
        return employeeService.getSalarySum();
    }

    @GetMapping("/salary/salaryHigherThan")
    public List<EmployeeDTO> getEmployeesWithSalaryMoreThan(@RequestParam("salary") double compareSalary) {
        return employeeService.getEmployeesWithSalaryMoreThan(compareSalary);
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/salary/all")
    public List<EmployeesInfo> findAllEmployeesInfo() {
        return employeeService.findAllEmployeesView();
    }

    @GetMapping
    public List<EmployeeDTO> returnEmployeesByPosition(@RequestParam("position") String position) {
        return employeeService.returnEmployeesByPosition(position);
    }

    @GetMapping("/{id}/fullInfo")
    public List<EmployeesInfo> returnEmployeesByIdInfo(@PathVariable Integer id) {
        return employeeService.returnAllEmployeesView(id);
    }

    @GetMapping("/page")
    public List<EmployeeDTO> returnEmployeesByPageNumber(@RequestParam("page") String page) {
        return employeeService.returnEmployeesByPageNumber(page);
    }
}
