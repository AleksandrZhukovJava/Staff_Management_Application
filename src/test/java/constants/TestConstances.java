package constants;

import com.github.javafaker.Faker;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.*;
import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.webLibrary.models.mapper.EmployeeDTOMapper;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;
import ru.skypro.lessons.springboot.webLibrary.models.projections.ReportInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestConstances {
    private static final Faker FAKER = new Faker();
    public static final Position POSITION_TEST_ONE = new Position(1, "Test Position");
    public static final Position POSITION_TEST_TWO = new Position(2, "Test Position Two");
    public static final Employee EMPLOYEE_TEST_ONE = new Employee(1, 120000.00, "Test Employee", POSITION_TEST_ONE );
    public static final Employee EMPLOYEE_TEST_TWO = new Employee(2, 2.00, "Test Employee One", POSITION_TEST_TWO);
    public static final List<Employee> EMPLOYEE_TEST_LIST = new ArrayList<>(){{
        add(EMPLOYEE_TEST_ONE);
        add(EMPLOYEE_TEST_TWO);
    }};
    public static final List<Position> POSITIONS_TEST_LIST = new ArrayList<>(){{
        add(POSITION_TEST_ONE);
        add(POSITION_TEST_TWO);
    }};
    public static final List<EmployeeDTO> EMPLOYEEDTO_TEST_LIST = EMPLOYEE_TEST_LIST.stream().map(EmployeeDTOMapper::fromEmployee).toList();
    public static final EmployeesInfo EMPLOYEEINFO_TEST_ONE = new EmployeesInfo(EMPLOYEE_TEST_ONE.getName(),EMPLOYEE_TEST_ONE.getSalary(),EMPLOYEE_TEST_ONE.getPosition().getName());
    public static final EmployeesInfo EMPLOYEEINFO_TEST_TWO = new EmployeesInfo(EMPLOYEE_TEST_TWO.getName(),EMPLOYEE_TEST_TWO.getSalary(),EMPLOYEE_TEST_TWO.getPosition().getName());

    public static final List<EmployeesInfo> EMPLOYEEINFO_TEST_LIST = new ArrayList<>(){{
        add(EMPLOYEEINFO_TEST_ONE);
        add(EMPLOYEEINFO_TEST_TWO);
    }};
    public static final int INCORRECT_ID = -1;
    public static final int CORRECT_ID = 1;
    public static final int TOO_BIG_ID = 100;
    public static final double CORRECT_SALARY = 20_000.00;
    public static final double INCORRECT_SALARY = -20_000.00;
    public static final Report REPORT_TEST = new Report(1, new byte[0]);
    public static final ReportInfo REPORT_INFO_TEST = new ReportInfo("Test position", 1L, 1.00, 1.00, 1.00);
    public static final ReportInfo REPORT_INFO_TEST_TWO = new ReportInfo("Test position two", 1L, 1.00, 1.00, 1.00);
    public static final List<ReportInfo> REPORT_INFO_TEST_LIST = new ArrayList<>(){{
        add(REPORT_INFO_TEST);
        add(REPORT_INFO_TEST_TWO);
    }};
    public static final String CORRECT_USERNAME_TEST = "Sashka";
    public static final String NOT_CORRECT_USERNAME_TEST = "AdminG***o";

    public static final User USER_TEST = new User (1,"12345","Sashka",true,null);
    public static List<EmployeesInfo> generateEmployeesInfo(){
        return Stream.generate(() -> new EmployeesInfo(FAKER.name().name(), FAKER.random().nextDouble(),FAKER.company().profession()))
                .limit(5)
                .collect(Collectors.toList());
    }
}
