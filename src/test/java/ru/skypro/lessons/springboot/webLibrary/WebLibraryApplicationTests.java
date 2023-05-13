package ru.skypro.lessons.springboot.webLibrary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.lessons.springboot.webLibrary.controller.EmployeeController;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.webLibrary.service.EmployeeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class WebLibraryApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeController myTestController;
    @Autowired
    private EmployeeRepository myTestRepository;
    @Autowired
    private EmployeeService myTestService;

    @Test
    @DisplayName("Beans created successfully ")
    void testBeansCreated() {
        assertThat(myTestController).isNotNull();
        assertThat(myTestRepository).isNotNull();
        assertThat(myTestService).isNotNull();
    }
    @Test
    @DisplayName("Employees with min salary returned successfully")
    void shouldReturnEmployeeWithMinSalary() throws Exception {

        mockMvc.perform(get("/employee/salary/min"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Oleg")))
                .andExpect(jsonPath("$[0].salary", is(10000.0)));
    }
    @Test
    @DisplayName("Employees with max salary returned successfully")
    void shouldReturnEmployeeWithMaxSalary() throws Exception {
        mockMvc.perform(get("/employee/salary/max"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Sasha")))
                .andExpect(jsonPath("$[0].salary", is(60000.00)))
                .andExpect(jsonPath("$[1].name", is("FakeSasha")))
                .andExpect(jsonPath("$[1].salary", is(60000.00)));
    }
    @Test
    @DisplayName("Summary salary returned correct")
    void shouldReturnSumSalary() throws Exception {
        mockMvc.perform(get("/employee/salary/sum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(250000.00)));
    }
    @Test
    @DisplayName("Employees with salaries above average returned successfully")
    void shouldReturnEmployeesWithAboveAverageSalary() throws Exception {
        mockMvc.perform(get("/employee/salary/high-salary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Petya")))
                .andExpect(jsonPath("$[0].salary", is(50000.00)))
                .andExpect(jsonPath("$[1].name", is("Sasha")))
                .andExpect(jsonPath("$[1].salary", is(60000.00)))
                .andExpect(jsonPath("$[2].name", is("FakeSasha")))
                .andExpect(jsonPath("$[2].salary", is(60000.00)));
    }
    @Test
    @DisplayName("Employees with salaries above average returned successfully")
    void shouldReturnEmployeesWithSalaryMoreThanAverage() throws Exception {
        mockMvc.perform(get("/employee/salary/salaryHigherThan?salary=" + 59999.00))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].name", is("Sasha")))
                .andExpect(jsonPath("$[0].salary", is(60000.00)))
                .andExpect(jsonPath("$[1].name", is("FakeSasha")))
                .andExpect(jsonPath("$[1].salary", is(60000.00)));


    }
    @Test
    @DisplayName("Employee returned successfully by id")
    void shouldReturnEmployeeById() throws Exception {
        mockMvc.perform(get("/employee/" + 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name", is("Oleg")))
                .andExpect(jsonPath("$.salary", is(10000.00)));
    }
    @Test
    @DisplayName("Employee was created and deleted successfully")
    void shouldDeleteEmployeeById() throws Exception {
        Employee testEmployee = new Employee("test", 1.00);
        myTestRepository.save(testEmployee);

        mockMvc.perform(delete("/employee/" + myTestRepository.returnAllEmployee()
                        .stream()
                        .filter(x -> x.getName().equals(testEmployee.getName()))
                        .mapToInt(Employee::getId)
                        .findFirst().orElse(0)
                ))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
