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
@RequestMapping("/admin/employee")
@AllArgsConstructor
@Tag(name = "Employees data base")
public class EmployeeAdminController {
    private final EmployeeService employeeService;
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
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createEmployeesByJson(MultipartFile file) throws IOException {
        employeeService.createEmployeesByJson(file);
    }
}
