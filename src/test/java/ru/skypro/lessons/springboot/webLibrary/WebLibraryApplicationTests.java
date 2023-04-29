package ru.skypro.lessons.springboot.webLibrary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.lessons.springboot.webLibrary.controller.EmployeeController;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepositoryImpl;
import ru.skypro.lessons.springboot.webLibrary.service.EmployeeService;
import ru.skypro.lessons.springboot.webLibrary.service.EmployeeServiceImpl;

import java.util.List;
import java.util.Objects;

@SpringBootTest
class WebLibraryApplicationTests {

    private final EmployeeRepository testEmployeeRepository = new EmployeeRepositoryImpl();
    private final EmployeeService testEmployeeService = new EmployeeServiceImpl(testEmployeeRepository);
    private final EmployeeController employeeController = new EmployeeController(testEmployeeService);

    @Test
    void contextLoads() {
    }
    @Test
    @DisplayName("Employee with min salary determined correctly")
    public void testEmployeeWithMinSalaryExist(){
            Employee expcectedEmployee = testEmployeeRepository.returnAllEmployee().get(7);
        for (Employee employee : Objects.requireNonNull(employeeController.getMinSalary())) {
            Assertions.assertEquals(expcectedEmployee.getSalary(), employee.getSalary());
        }
    }
    @Test
    @DisplayName("Employee with max salary determined correctly")
    public void testEmployeeWithMaxSalaryExist(){
        Employee expcectedEmployee = testEmployeeRepository.returnAllEmployee().get(5);
        for (Employee employee : Objects.requireNonNull(employeeController.getMaxSalary())) {
            Assertions.assertEquals(expcectedEmployee.getSalary(), employee.getSalary());
        }
    }
    @Test
    @DisplayName("Sum salary determined correctly")
    public void testSalarySumCorrect(){
        Assertions.assertEquals(529000, employeeController.getSumSalary());
    }
    @Test
    @DisplayName("List of employee exists and returns")
    public void testListOfEmployeeExisting(){
        List<Employee> list = employeeController.getAboveAveragePaidEmployees();
        Assertions.assertNotNull(list);
    }
}
