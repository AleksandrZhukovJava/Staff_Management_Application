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
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
    @DisplayName("Employees with max salary returned successfully")
    public void testGetMaxSalaryRoute() throws Exception {
        mockMvc.perform(get("/employee/salary/max"))
                .andExpect(status().isOk())
                .andExpect(view().name("max"))
                .andExpect(model().attributeExists("employees"))
                .andExpect(model().attribute("employees", hasSize(2)))
                .andExpect(model().attribute("employees", hasItems(
                        allOf(
                                hasProperty("name", is("Mr.Director")),
                                hasProperty("salary", is(40000.00))
                        ),
                        allOf(
                                hasProperty("name", is("Mr.Accountant")),
                                hasProperty("salary", is(40000.00))
                        )
                )));
    }
    @Test
    @DisplayName("Employees with min salary returned successfully")
    public void testGetMinSalaryRoute() throws Exception {
        mockMvc.perform(get("/employee/salary/min"))
                .andExpect(status().isOk())
                .andExpect(view().name("min"))
                .andExpect(model().attributeExists("employees"))
                .andExpect(model().attribute("employees", hasSize(2)))
                .andExpect(model().attribute("employees", hasItems(
                        allOf(
                                hasProperty("name", is("Uborshik")),
                                hasProperty("salary", is(2000.00))
                        ),
                        allOf(
                                hasProperty("name", is("PoorGuyFake")),
                                hasProperty("salary", is(2000.00))
                        )
                )));
    }

    @Test
    @DisplayName("Employees added to list")
    void shouldReturnAllEmployee() {
        assertThat(session.createQuery("from Employee", Employee.class).getResultList().size()).isNotEqualTo(0);
    }
    @Test
    @DisplayName("Summary salary returned correct")
    void shouldReturnSumSalary() throws Exception {
        mockMvc.perform(get("/employee/salary/sum"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("sum",is(144000.00)));
    }

    @Test
    @DisplayName("Employees with salaries above average returned successfully")
    void shouldReturnEmployeesWithAboveAverageSalary() throws Exception {
        mockMvc.perform(get("/employee/high-salary"))
                .andExpect(status().isOk())
                .andExpect(view().name("high-salary"))
                .andExpect(model().attributeExists("employees"))
                .andExpect(model().attribute("employees", hasSize(3)))
                .andExpect(model().attribute("employees", hasItems(
                        allOf(
                                hasProperty("name", is("Mr.Director")),
                                hasProperty("salary", is(40000.00))
                        ),
                        allOf(
                                hasProperty("name", is("Mr.Accountant")),
                                hasProperty("salary", is(40000.00))
                        ),
                        allOf(
                                hasProperty("name", is("Konstantin")),
                                hasProperty("salary", is(25000.00))
                        )
                )));
    }
}

