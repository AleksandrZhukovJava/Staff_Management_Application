package ru.skypro.lessons.springboot.webLibrary.service;

import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.webLibrary.models.projections.projections.EmployeesInfo;

import java.util.List;

public interface EmployeeService {

    double getSalarySum();
    List<EmployeeDTO> getMinSalaryEmployee();
    List<EmployeeDTO> getMaxSalaryEmployee();
    List<EmployeeDTO> getAboveAveragePaidEmployees();
    List<EmployeeDTO> getEmployeesWithSalaryMoreThan(double salary);
    EmployeeDTO getEmployeeById(Integer id);
    void deleteEmployeeById(Integer id);
    void changeEmployeeById(EmployeeDTO employee, Integer id);
    void createEmployees(List<EmployeeDTO> listOfNewEmployeesDTO);
    List<EmployeesInfo> findAllEmployeesView();
    List<EmployeeDTO> returnEmployeesByPosition(String position);
    List<EmployeesInfo> returnAllEmployeesView(Integer id);
    List<EmployeeDTO> returnEmployeesByPageNumber(String number);
}
