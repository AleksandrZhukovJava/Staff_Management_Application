package ru.skypro.lessons.springboot.webLibrary.service.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Employee;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Position;
import ru.skypro.lessons.springboot.webLibrary.exceptions.customexceptions.IllegalIdException;
import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.webLibrary.models.mapper.EmployeeDTOMapper;
import ru.skypro.lessons.springboot.webLibrary.models.mapper.EmployeeInfoMapper;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static constants.TestConstances.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepositoryMock;
    @InjectMocks
    private EmployeeServiceImpl out;

    public static Stream<Arguments> provideParamsForChangeEmployeeByIdTestMethod() {
        return Stream.of(
                Arguments.of(new Employee(-1, 1.00, "Test", new Position(1, "Test")), new IllegalIdException()),
                Arguments.of(new Employee(null, 1.00, "Test", new Position(1, "Test")), new IllegalIdException()),
                Arguments.of(new Employee(1, 1.00, "", new Position(1, "Test")), new IllegalArgumentException()),
                Arguments.of(new Employee(1, 1.00, null, new Position(1, "Test")), new IllegalArgumentException()),
                Arguments.of(new Employee(1, 1.00, "   ", new Position(1, "Test")), new IllegalArgumentException()),
                Arguments.of(new Employee(1, -1.00, "Test", new Position(1, "Test")), new IllegalArgumentException())
        );
    }

    public static Stream<Arguments> provideParamsForReturnEmployeesByPositionTestMethod() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of((String) null),
                Arguments.of("    ")
        );
    }

    @DisplayName("Returned correct sum of salaries and call repository once")
    @Test
    void getSalarySum_CorrectSum_ShouldCallRepository() {

        when(employeeRepositoryMock.returnSumSalary())
                .thenReturn(CORRECT_SALARY);

        Double actual = out.getSalarySum();

        assertEquals(CORRECT_SALARY, actual);

        verify(employeeRepositoryMock, times(1)).returnSumSalary();

    }

    @DisplayName("Returned min salary employees and call repository once")
    @Test
    void returnMinSalaryEmployees_CorrectEmployees_ShouldCallRepository() {
        when(employeeRepositoryMock.returnMinSalaryEmployees())
                .thenReturn(EMPLOYEE_TEST_LIST);

        List<EmployeeDTO> actual = out.getMinSalaryEmployee();

        assertEquals(EMPLOYEEDTO_TEST_LIST, actual);

        verify(employeeRepositoryMock, times(1)).returnMinSalaryEmployees();

    }

    @DisplayName("Returned max salary employees and call repository once")
    @Test
    void getMaxSalaryEmployee_CorrectEmployees_ShouldCallRepository() {
        when(employeeRepositoryMock.returnMaxSalaryEmployees())
                .thenReturn(EMPLOYEE_TEST_LIST);

        List<EmployeeDTO> actual = out.getMaxSalaryEmployee();

        assertEquals(EMPLOYEEDTO_TEST_LIST, actual);

        verify(employeeRepositoryMock, times(1)).returnMaxSalaryEmployees();

    }

    @DisplayName("Returned above average paid employees and call repository once")
    @Test
    void getAboveAveragePaidEmployees_CorrectEmployees_ShouldCallRepository() {
        when(employeeRepositoryMock.getAboveAveragePaidEmployees())
                .thenReturn(EMPLOYEE_TEST_LIST);

        List<EmployeeDTO> actual = out.getAboveAveragePaidEmployees();

        assertEquals(EMPLOYEEDTO_TEST_LIST, actual);

        verify(employeeRepositoryMock, times(1)).getAboveAveragePaidEmployees();

    }

    @DisplayName("Returned employees with salary bigger than coming and call repository once")
    @Test
    void getEmployeesWithSalaryMoreThan_CorrectEmployeesAnySum_ShouldCallRepository() {
        when(employeeRepositoryMock.findAllBySalaryIsBiggerThan(eq(CORRECT_SALARY)))
                .thenReturn(EMPLOYEE_TEST_LIST);

        List<EmployeeDTO> actual = out.getEmployeesWithSalaryMoreThan(CORRECT_SALARY);

        assertEquals(EMPLOYEEDTO_TEST_LIST, actual);

        verify(employeeRepositoryMock, times(1)).findAllBySalaryIsBiggerThan(anyDouble());
    }

    @DisplayName("Throw exception if salary to search less than zero")
    @Test
    void getEmployeesWithSalaryMoreThan_NotCorrectSum_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> out.getEmployeesWithSalaryMoreThan(UNCORRECTED_SALARY)
        );
    }

    @DisplayName("Returned employee by id and call repository once")
    @Test
    void getEmployeeById_CorrectEmployeeAnyId_ShouldCallRepository() {
        EmployeeDTO expected = EmployeeDTOMapper.fromEmployee(EMPLOYEE_TEST_ONE);

        when(employeeRepositoryMock.getById(eq(CORRECT_ID)))
                .thenReturn(EMPLOYEE_TEST_ONE);

        EmployeeDTO actual = out.getEmployeeById(CORRECT_ID);

        assertEquals(expected, actual);

        verify(employeeRepositoryMock, times(1)).getById(CORRECT_ID);

    }

    @DisplayName("Throw exception if id to search less than zero")
    @Test
    void getEmployeeById_NotCorrectId_ShouldThrowException() {
        assertThrows(IllegalIdException.class,
                () -> out.getEmployeeById(UNCORRECTED_ID)
        );
    }

    @DisplayName("Сall repository once than delete employee")
    @Test
    void deleteById_ShouldCallRepository() {
        doNothing().when(employeeRepositoryMock).deleteById(CORRECT_ID);

        out.deleteEmployeeById(CORRECT_ID);

        verify(employeeRepositoryMock, times(1)).deleteById(CORRECT_ID);
    }

    @DisplayName("Does not delete employee, throw exception if id to search less than zero")
    @Test
    void deleteById_NotCorrectId_ShouldThrowException() {
        assertThrows(IllegalIdException.class,
                () -> out.deleteEmployeeById(UNCORRECTED_ID)
        );
    }

    @DisplayName("Change employee by id and call repository twice")
    @Test
    void changeEmployeeById_CorrectEmployee_ShouldCallRepositoryTwice() {
        Optional<Employee> employeeOptional = Optional.of(EMPLOYEE_TEST_ONE);

        when(employeeRepositoryMock.save(eq(EMPLOYEE_TEST_ONE)))
                .thenReturn(EMPLOYEE_TEST_ONE);
        when(employeeRepositoryMock.findById(eq(CORRECT_ID)))
                .thenReturn(employeeOptional);

        out.changeEmployeeById(EmployeeDTOMapper.fromEmployee(EMPLOYEE_TEST_ONE), CORRECT_ID);

        verify(employeeRepositoryMock, times(1)).save(EMPLOYEE_TEST_ONE);
        verify(employeeRepositoryMock, times(1)).findById(CORRECT_ID);
    }

    @DisplayName("Does not change employee, throw exception on not correct id and call repository only once")
    @Test
    void changeEmployeeById_NotCorrectId_ShouldThrowException_ShouldNotCallSaveMethod() {
        when(employeeRepositoryMock.findById(eq(UNCORRECTED_ID)))
                .thenThrow(IllegalIdException.class);
        ;

        assertThrows(IllegalIdException.class,
                () -> out.changeEmployeeById(EmployeeDTOMapper.fromEmployee(EMPLOYEE_TEST_ONE), UNCORRECTED_ID)
        );

        verify(employeeRepositoryMock, times(1)).findById(UNCORRECTED_ID);
        verify(employeeRepositoryMock, times(0)).save(EMPLOYEE_TEST_ONE);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForChangeEmployeeByIdTestMethod")
    @DisplayName("Does not change employee by id and throws different exceptions on not correct employees, does not call repository")
    void changeEmployeeById_NotCorrectModels_ShouldThrowExceptions(Employee testEmployee, Exception exception) {
        assertThrows(exception.getClass(), () -> out.changeEmployeeById(EmployeeDTOMapper.fromEmployee(testEmployee), CORRECT_ID));
        verify(employeeRepositoryMock, times(0)).save(EMPLOYEE_TEST_ONE);
    }

    @DisplayName("Create employees by view and calls repository at least once")
    @Test
    void createEmployees_ShouldCallRepositoryAtLeastTwice() {
        when(employeeRepositoryMock.saveAll(new ArrayList<>() {{
            add(EmployeeInfoMapper.toEmployee(EMPLOYEEINFO_TEST_LIST.get(0)));
            add(EmployeeInfoMapper.toEmployee(EMPLOYEEINFO_TEST_LIST.get(1)));
        }}))
                .thenReturn(EMPLOYEE_TEST_LIST);
        when(employeeRepositoryMock.findPositionByName(EMPLOYEE_TEST_ONE.getPosition().getName()))
                .thenReturn(new Position(EMPLOYEE_TEST_ONE.getPosition().getName()));
        when(employeeRepositoryMock.findPositionByName(EMPLOYEE_TEST_TWO.getPosition().getName()))
                .thenReturn(new Position(EMPLOYEE_TEST_TWO.getPosition().getName()));

        out.createEmployees(EMPLOYEEINFO_TEST_LIST);

        verify(employeeRepositoryMock, atLeast(1)).findPositionByName(any());
        verify(employeeRepositoryMock, times(1)).saveAll(any());

    }

    @DisplayName("Find all employees and calls repository once")
    @Test
    void findAllEmployeesView_ShouldCallRepositoryOnce() {
        when(employeeRepositoryMock.findAllEmployeesView())
                .thenReturn(EMPLOYEEINFO_TEST_LIST);

        List<EmployeesInfo> actual = out.findAllEmployeesView();

        assertEquals(EMPLOYEEINFO_TEST_LIST, actual);

        verify(employeeRepositoryMock, times(1)).findAllEmployeesView();
    }

    @DisplayName("Find all employees by position name and calls repository once")
    @Test
    void returnEmployeesByPosition_CorrectPositionName_ShouldCallRepositoryOnce() {
        List<Employee> test = new ArrayList<>() {{
            add(EMPLOYEE_TEST_ONE);
        }};
        when(employeeRepositoryMock.returnAllByPositionName(eq(POSITION_TEST_ONE.getName())))
                .thenReturn(test);

        List<EmployeeDTO> actual = out.returnEmployeesByPosition(POSITION_TEST_ONE.getName());
        List<EmployeeDTO> expected = test.stream().map(EmployeeDTOMapper::fromEmployee).toList();

        assertIterableEquals(expected,actual);

        verify(employeeRepositoryMock, times(1)).returnAllByPositionName(POSITION_TEST_ONE.getName());
    }

    @DisplayName("Find all employees by position id and calls repository once")
    @Test
    void returnEmployeesByPosition_CorrectPositionId_ShouldCallRepositoryOnce() {
        List<Employee> testList = new ArrayList<>() {{
            add(EMPLOYEE_TEST_ONE);
        }};
        when(employeeRepositoryMock.returnAllByPositionId(eq(POSITION_TEST_ONE.getId())))
                .thenReturn(testList);

        List<EmployeeDTO> actual = out.returnEmployeesByPosition(POSITION_TEST_ONE.getId().toString());
        List<EmployeeDTO> expected = testList.stream().map(EmployeeDTOMapper::fromEmployee).toList();

        assertIterableEquals(expected,actual);

        verify(employeeRepositoryMock, times(1)).returnAllByPositionId(POSITION_TEST_ONE.getId());
    }

    @DisplayName("Find all employees if position name wrong and calls repository once")
    @ParameterizedTest
    @MethodSource("provideParamsForReturnEmployeesByPositionTestMethod")
    void returnEmployeesByPosition_NotCorrectPositionName_ShouldCallRepositoryOnce(String testArguments) {
        when(employeeRepositoryMock.findAll())
                .thenReturn(EMPLOYEE_TEST_LIST);

        List<EmployeeDTO> actual = out.returnEmployeesByPosition(testArguments);
        List<EmployeeDTO> expected = EMPLOYEE_TEST_LIST.stream().map(EmployeeDTOMapper::fromEmployee).toList();

        assertIterableEquals(expected,actual);

        verify(employeeRepositoryMock, times(1)).findAll();
    }

    @DisplayName("Returned employee view by id and call repository once")
    @Test
    void returnAllEmployeesView_CorrectId_ShouldCallRepositoryOnce() {
        when(employeeRepositoryMock.findEmployeeByIdView(eq(CORRECT_ID)))
                .thenReturn(EMPLOYEEINFO_TEST_LIST);

        out.returnAllEmployeesView(CORRECT_ID);

        verify(employeeRepositoryMock, times(1)).findEmployeeByIdView(CORRECT_ID);
    }

    @DisplayName("Throw exception if id not correct, does not call repository")
    @Test
    void returnAllEmployeesView_NotCorrectId_ShouldNotCallRepositoryOnce() {
        assertThrows(IllegalIdException.class,
                () -> out.returnAllEmployeesView(UNCORRECTED_ID));

        verify(employeeRepositoryMock, times(0)).findEmployeeByIdView(CORRECT_ID);
    }

    @DisplayName("Return first page by page number and call repository once")
    @Test
    void returnEmployeesByPageNumber_CorrectPageNumber_ShouldNCallRepositoryOnce() {
        Pageable pageRequest = PageRequest.of(0, 10);
        when(employeeRepositoryMock.findAll(eq(pageRequest)))
                .thenReturn(new PageImpl<>(EMPLOYEE_TEST_LIST));

        List<EmployeeDTO> expected = out.returnEmployeesByPageNumber("0");

        assertEquals(EMPLOYEEDTO_TEST_LIST, expected);

        verify(employeeRepositoryMock, times(1)).findAll(pageRequest);
    }

    @DisplayName("Return blank page by wrong number of page and call repository once")
    @Test
    void returnEmployeesByPageNumber_NotCorrectPageNumber_ShouldNCallRepositoryOnce() {
        Pageable pageRequest = PageRequest.of(2, 10);
        when(employeeRepositoryMock.findAll(eq(pageRequest)))
                .thenReturn(Page.empty());

        List<EmployeeDTO> expected = out.returnEmployeesByPageNumber("2");

        assertEquals(Collections.EMPTY_LIST, expected);

        verify(employeeRepositoryMock, times(1)).findAll(pageRequest);
    }

    @DisplayName("Create employees and call repository once")
    @Test
    @SneakyThrows
    void createEmployeesByJson_CorrectJson_ShouldNCallRepositoryOnce() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        json = objectMapper.writeValueAsString(EMPLOYEE_TEST_LIST);

        when(employeeRepositoryMock.saveAll(eq(EMPLOYEE_TEST_LIST)))
                .thenReturn(any());

        out.createEmployeesByJson(new MockMultipartFile("test",json.getBytes()));

        verify(employeeRepositoryMock, times(1)).saveAll(EMPLOYEE_TEST_LIST);

    }

    @DisplayName("Throw exception if JSON is not correct and does not call the repository")
    @Test
    @SneakyThrows
    void createEmployeesByJson_NotCorrectJson_ShouldNotCallRepository() {
        assertThrows(Exception.class, () -> out.createEmployeesByJson(new MockMultipartFile("test", "Oops".getBytes())));
        verify(employeeRepositoryMock, times(0)).saveAll(any());
    }
}

