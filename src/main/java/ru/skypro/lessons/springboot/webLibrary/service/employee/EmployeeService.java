package ru.skypro.lessons.springboot.webLibrary.service.employee;

import com.fasterxml.jackson.core.JacksonException;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;

import java.io.IOException;
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
    void createEmployees(List<EmployeesInfo> listOfNewEmployeesDTO);
    List<EmployeesInfo> findAllEmployeesView();
    List<EmployeeDTO> returnEmployeesByPosition(String position);
    EmployeesInfo returnAllEmployeesView(Integer id);
    List<EmployeeDTO> returnEmployeesByPageNumber(String number);
    void createEmployeesByJson(MultipartFile file) throws IOException;
}
