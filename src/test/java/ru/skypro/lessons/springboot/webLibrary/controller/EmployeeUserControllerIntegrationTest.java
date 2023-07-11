package ru.skypro.lessons.springboot.webLibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;

import static constants.TestConstances.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
public class EmployeeUserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    void contextLoads() {
    }

    @Test
    void returnCorrectHighSalaryEmployees() throws Exception {
        mockMvc.perform(get("/user/employee/salary/high-salary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Nastya"))
                .andExpect(jsonPath("$[0].salary").value(100000));
    }

    @Test
    void givenEmployeeInDatabase_thenReturnCorrectHighSalaryEmployees() throws Exception {
        employeeRepository.save(EMPLOYEE_TEST_ONE);

        mockMvc.perform(get("/user/employee/salary/high-salary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test Employee"))
                .andExpect(jsonPath("$[0].salary").value(120000.00))
                .andExpect(jsonPath("$[1].name").value("Nastya"))
                .andExpect(jsonPath("$[1].salary").value(100000.00));
    }
    @Test
    void deleteAllEmployeeFromDataBase_callEmployeesHighSalaryEmployees_thenReturnEmptyList() throws Exception {
        employeeRepository.deleteAll();

        mockMvc.perform(get("/user/employee/withHighestSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void returnCorrectMaxSalaryEmployee() throws Exception {
        mockMvc.perform(get("/user/employee/withHighestSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Nastya"))
                .andExpect(jsonPath("$[0].salary").value(100000.00));
    }

    @Test
    void givenEmployeeWithMaxSalaryInDatabase_thenReturnCorrectMaxSalaryEmployee() throws Exception {
        employeeRepository.save(EMPLOYEE_TEST_ONE);

        mockMvc.perform(get("/user/employee/withHighestSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Employee"))
                .andExpect(jsonPath("$[0].salary").value(120000.00));
    }
    @Test
    void deleteAllEmployeeFromDataBase_callEmployeesWithMaxSalary_thenReturnEmptyList() throws Exception {
        employeeRepository.deleteAll();

        mockMvc.perform(get("/user/employee/withHighestSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void returnCorrectMinSalaryEmployee() throws Exception {
        mockMvc.perform(get("/user/employee/salary/min"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Oleg"))
                .andExpect(jsonPath("$[0].salary").value(19000.00));
    }

    @Test
    void givenEmployeeWithMinSalaryInDatabase_thenReturnCorrectMinSalaryEmployee() throws Exception {
        employeeRepository.save(EMPLOYEE_TEST_TWO);

        mockMvc.perform(get("/user/employee/salary/min"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Employee One"))
                .andExpect(jsonPath("$[0].salary").value(2.00));
    }
    @Test
    void deleteAllEmployeeFromDataBase_callEmployeesWithMinSalary_thenReturnEmptyList() throws Exception {
        employeeRepository.deleteAll();

        mockMvc.perform(get("/user/employee/salary/min"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void returnCorrectSumSalaryOfEmployees() throws Exception {

        mockMvc.perform(get("/user/employee/salary/sum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(222000.00));
    }

    @Test
    void givenAnotherEmployeeInDatabase_thenReturnCorrectSumSalaryOfEmployees() throws Exception {
        employeeRepository.save(EMPLOYEE_TEST_ONE);

        mockMvc.perform(get("/user/employee/salary/sum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(323000.00));
    }

    @Test
    void deleteAllEmployeeFromDataBase_thenReturnZeroSumOfSalary() throws Exception {
        employeeRepository.deleteAll();

        mockMvc.perform(get("/user/employee/salary/sum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(0));
    }
    @Test
    void returnCorrectEmployeesWithSalaryMoreThan() throws Exception {
        mockMvc.perform(get("/user/employee/salary/salaryHigherThan").param("salary","90000.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Nastya"))
                .andExpect(jsonPath("$[0].salary").value(100000.00));
    }
    @Test
    void givenAnotherEmployeeInDatabase_thenReturnCorrectEmployeesWithSalaryMoreThan() throws Exception {
        employeeRepository.save(EMPLOYEE_TEST_ONE);

        mockMvc.perform(get("/user/employee/salary/salaryHigherThan").param("salary","90000.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test Employee"))
                .andExpect(jsonPath("$[0].salary").value(120000.00))
                .andExpect(jsonPath("$[1].name").value("Nastya"))
                .andExpect(jsonPath("$[1].salary").value(100000.00));

    }
    @Test //tut
    void givenWrongParamToEndpoint_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(get("/user/employee/salary/salaryHigherThan").param("salary",""))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void returnCorrectEmployeeById() throws Exception {
        mockMvc.perform(get("/user/employee/" + CORRECT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("Oleg"))
                .andExpect(jsonPath("$.salary").value(19000.00));
    }
    @Test
    void returnCorrectEmployeeByIdTEst() throws Exception {
        employeeRepository.save(EMPLOYEE_TEST_ONE);
        mockMvc.perform(get("/user/employee/" + CORRECT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("Test Employee"))
                .andExpect(jsonPath("$.salary").value(120000.00));
    }
    @Test//tut
    void deleteAllEmployeeFromDataBase_thenReturnBadRequestStatus() throws Exception {
        employeeRepository.deleteAll();

        mockMvc.perform(get("/user/employee/" + CORRECT_ID))
                .andExpect(status().is4xxClientError());
    }
    @Test//tut
    void givenWrongIdToEndpoint_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(get("/user/employee/" + INCORRECT_ID))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void returnCorrectEmployeesInfo() throws Exception {
        mockMvc.perform(get("/user/employee/salary/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(6));
    }
    @Test
    void deleteEmployeesFromDataBase_callReturnAllEmployeeInfoMethod_thanReturnEmptyList() throws Exception {
        employeeRepository.deleteAll();

        mockMvc.perform(get("/user/employee/salary/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    void  givePositionById_thenReturnCorrectEmployeesByPosition() throws Exception {
        String positionId = "3";
        mockMvc.perform(get("/user/employee?position=" + positionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Nastya"))
                .andExpect(jsonPath("$[0].salary").value(100000.00));
    }
    @Test
    void  givePositionByName_thenReturnCorrectEmployeesByPosition() throws Exception {
        String positionName = "Director";
        mockMvc.perform(get("/user/employee?position=" + positionName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Nastya"))
                .andExpect(jsonPath("$[0].salary").value(100000.00));
    }
    @Test
    void  giveBlankPosition_thenReturnAllEmployees() throws Exception {
        mockMvc.perform(get("/user/employee?position="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(6));
    }
    @Test
    void  giveNotExistedId_thenReturnEmptyList() throws Exception {
        String positionId = "100";
        mockMvc.perform(get("/user/employee?position=" + positionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    void  giveWrongPositionId_thenReturnEmptyList() throws Exception {
        mockMvc.perform(get("/user/employee?position=" + INCORRECT_ID))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void giveNotExistedPosition_thenReturnEmptyList() throws Exception {
        String positionName = "Not correct position";
        mockMvc.perform(get("/user/employee?position=" + positionName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    void returnCorrectEmployeeInfoById() throws Exception {
        mockMvc.perform(get(String.format("/user/employee/%s/fullInfo",CORRECT_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("Oleg"))
                .andExpect(jsonPath("$.salary").value(19000.00));
    }

    @Test
    void giveWrongId_thenReturn400Status() throws Exception {
        mockMvc.perform(get(String.format("/user/employee/%s/fullInfo", INCORRECT_ID)))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void giveNotExistedId_thenReturnEmptyJson() throws Exception {
        String notExistedId = "100";
        mockMvc.perform(get(String.format("/user/employee/%s/fullInfo", notExistedId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
    @Test
    void givenFirstPageNumber_thenReturnCorrectArray() throws Exception {
        mockMvc.perform(get("/user/employee/page?page=" + 0))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(6));
    }
    @Test
    void givenTooBigPageNumber_thenReturnEmptyArray() throws Exception {
        mockMvc.perform(get("/user/employee/page?page=" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    void givenEmptyPageNumber_thenReturnCorrectArray() throws Exception {
        mockMvc.perform(get("/user/employee/page?page="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(6));
    }
    @Test
    void givenWrongPageNumber_thenReturnCorrectArray() throws Exception {
        mockMvc.perform(get("/user/employee/page?page=" + -1))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void givenIncorrectPageNumber_thenReturnCorrectArray() throws Exception {
        String incorrectPageNumber = "page";
        mockMvc.perform(get("/user/employee/page?page=" + incorrectPageNumber))
                .andExpect(status().is4xxClientError());
    }

}
