package ru.skypro.lessons.springboot.webLibrary.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;
import ru.skypro.lessons.springboot.webLibrary.service.employee.EmployeeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
@Tag(name = "Employees data base actions")
public class EmployeeController {
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
    @DeleteMapping("/{id}")
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
    }
    @PutMapping("/{id}")
    public void changeEmployeeById(@PathVariable Integer id, @RequestBody EmployeeDTO employeeDTO) {
        employeeService.changeEmployeeById(employeeDTO, id);
    }
    @PostMapping("/")
    public void createEmployees(@RequestBody List<EmployeesInfo> listOfNewEmployeesDTO) {
        employeeService.createEmployees(listOfNewEmployeesDTO);
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
    @PostMapping(value = "/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createEmployeesByJson(@RequestParam("json") MultipartFile file) throws IOException {
        employeeService.createEmployeesByJson(file);
    }
}
