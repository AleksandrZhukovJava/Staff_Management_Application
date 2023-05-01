package ru.skypro.lessons.springboot.webLibrary;

import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.webLibrary.config.HibernateSessionUtil;
import ru.skypro.lessons.springboot.webLibrary.controller.EmployeeController;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.webLibrary.service.EmployeeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
    private final Session session = HibernateSessionUtil.getSessionFactory().openSession();

    @Test
    @DisplayName("Beans created successfully ")
    void testBeansCreated() {
        assertThat(myTestController).isNotNull();
        assertThat(myTestRepository).isNotNull();
        assertThat(myTestService).isNotNull();
    }
    @Test
    void shouldReturnAllEmployee(){
        assertThat(session.createQuery("from Employee", Employee.class).getResultList().size()).isNotEqualTo(0);
    }
    @Test
    @DisplayName("Employees with min salary returned successfully")
    void shouldReturnEmployeeWithMinSalary() throws Exception {

        mockMvc.perform(get("/employee/salary/min"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Uborshik")))
                .andExpect(jsonPath("$[0].salary", is(2000.0)))
                .andExpect(jsonPath("$[1].name", is("PoorGuyFake")))
                .andExpect(jsonPath("$[1].salary", is(2000.0)));
    }
    @Test
    @DisplayName("Employees with max salary returned successfully")
    void shouldReturnEmployeeWithMaxSalary() throws Exception {

        mockMvc.perform(get("/employee/salary/max"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Mr.Director")))
                .andExpect(jsonPath("$[0].salary", is(40000.00)))
                .andExpect(jsonPath("$[1].name", is("Mr.Accountant")))
                .andExpect(jsonPath("$[1].salary", is(40000.00)));
    }
    @Test
    @DisplayName("Summary salary returned correct")
    void shouldReturnSumSalary() throws Exception {
        mockMvc.perform(get("/employee/salary/sum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(144000.00)));
    }
    @Test
    @DisplayName("Employees with salaries above average returned successfully")
    void shouldReturnEmployeesWithAboveAverageSalary() throws Exception {
        mockMvc.perform(get("/employee/high-salary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Konstantin")))
                .andExpect(jsonPath("$[0].salary", is(25000.00)))
                .andExpect(jsonPath("$[1].name", is("Mr.Director")))
                .andExpect(jsonPath("$[1].salary", is(40000.00)))
                .andExpect(jsonPath("$[2].name", is("Mr.Accountant")))
                .andExpect(jsonPath("$[2].salary", is(40000.00)));
    }
}

