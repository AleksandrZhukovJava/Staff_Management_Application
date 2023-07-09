package ru.skypro.lessons.springboot.webLibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Employee;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Position;
import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.webLibrary.models.mapper.EmployeeDTOMapper;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.webLibrary.repository.PositionRepository;

import java.util.List;

import static constants.TestConstances.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN", "USER"})
@Transactional
public class EmployeeAdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deleteEmployeeById_thenCheckActualListDoesNotContainEmployee() throws Exception {
        Employee deletedEmployee = employeeRepository.findAllEmployees().stream().filter(x -> x.getId().equals(CORRECT_ID)).findFirst().get();

        mockMvc.perform(delete("/admin/employee/" + CORRECT_ID))
                .andExpect(status().isOk());
        List<Employee> actual = employeeRepository.findAllEmployees();

        assertThat(actual).doesNotContain(deletedEmployee);
    }

    @Test
    void givenIncorrectId_thenReturnEmployeeByThisId_thenReturn400Status() throws Exception {
        mockMvc.perform(delete("/admin/employee/" + INCORRECT_ID))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void givenTooBigId_thenDoNothing_notThrowException() throws Exception {
        mockMvc.perform(delete("/admin/employee/" + TOO_BIG_ID))
                .andExpect(status().isOk());

    }

    @Test
    void changeEmployeeById_thenEmployeeWasChanged() throws Exception {
        EmployeeDTO employeeDTO = EmployeeDTOMapper.fromEmployee(EMPLOYEE_TEST_ONE);

        mockMvc.perform(put("/admin/employee/" + CORRECT_ID)
                        .content(objectMapper.writeValueAsString(employeeDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        Employee actual = employeeRepository.getEmployeeById(CORRECT_ID).get();

        assertThat(actual.getName()).isNotEqualTo("Oleg");
        assertThat(actual.getSalary()).isNotEqualTo(19000.00);
    }

    @Test
    void givenIncorrectId_thanReturn400Status_thenEmployeesNotChanged() throws Exception {
        EmployeeDTO employeeDTO = EmployeeDTOMapper.fromEmployee(EMPLOYEE_TEST_ONE);
        List<Employee> expected = employeeRepository.findAllEmployees();

        mockMvc.perform(put("/admin/employee/" + INCORRECT_ID)
                        .content(objectMapper.writeValueAsString(employeeDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        List<Employee> actual = employeeRepository.findAllEmployees();


        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void givenTooBigId_thanReturn400Status_thenEmployeesNotChanged() throws Exception {
        EmployeeDTO employeeDTO = EmployeeDTOMapper.fromEmployee(EMPLOYEE_TEST_ONE);
        List<Employee> expected = employeeRepository.findAllEmployees();

        mockMvc.perform(put("/admin/employee/" + TOO_BIG_ID)
                        .content(objectMapper.writeValueAsString(employeeDTO))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        List<Employee> actual = employeeRepository.findAllEmployees();


        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void addEmployeeInDataBaseByEmployeeInfo() throws Exception {
        List<EmployeesInfo> inputData = generateEmployeesInfo();
        List<Employee> expected = employeeRepository.findAllEmployees();
        inputData.stream()
                .map(x -> new Employee(null, x.getSalary(), x.getName(), null))
                .forEach(expected::add);

        mockMvc.perform(post("/admin/employee/")
                        .content(objectMapper.writeValueAsString(inputData))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        List<Employee> actual = employeeRepository.findAllEmployees();

        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "position")
                .containsExactlyInAnyOrderElementsOf(expected);
    }
    @Test
    void addEmployeesWithNewPositions_thenRepositoryAddNewPosition() throws Exception {
        List<EmployeesInfo> inputData = generateEmployeesInfo();
        List<String> expected = inputData.stream().map(EmployeesInfo::getPositionName).toList();

        mockMvc.perform(post("/admin/employee/")
                        .content(objectMapper.writeValueAsString(inputData))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        List<String> actual = positionRepository.findAll().stream().map(Position::getName).toList();

        assertThat(actual).containsAll(expected);
    }
    @Test
    void givenNullExceptList_thenReturn500Status() throws Exception {
        mockMvc.perform(post("/admin/employee/")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
    @Test
    void addEmployeeInDataBaseByJson() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "file.json",
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(EMPLOYEE_TEST_LIST)
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/employee/upload")
                        .file(multipartFile)
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk());

        List<Employee> actual = employeeRepository.findAllEmployees();

        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("position")
                .containsAnyElementsOf(EMPLOYEE_TEST_LIST);
    }
    @Test
    void givenIncorrectJson_thenReturn400Status() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "file.json",
                APPLICATION_JSON_VALUE,
                new byte[0]
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/employee/upload")
                        .file(multipartFile)
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().is4xxClientError());

    }
}

