package ru.skypro.lessons.springboot.webLibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Employee;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Position;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, PagingAndSortingRepository<Employee, Integer> {

    @Query("SELECT e,p FROM Employee e JOIN FETCH e.position p WHERE e.salary = (SELECT MIN(e.salary) FROM Employee e)")
    List<Employee> returnMinSalaryEmployees();
    @Query("SELECT e,p FROM Employee e JOIN FETCH Position p ON e.position = p where e.salary = (Select max(salary) from Employee)")
    List<Employee> returnMaxSalaryEmployees();
    @Query("SELECT max(e.salary) FROM Employee e where e.position.id = :id")
    double returnMaxSalaryByPosition(@Param("id") Integer id);
    @Query("SELECT min(e.salary) FROM Employee e where e.position.id = :id")
    double returnMinSalaryByPosition(@Param("id") Integer id);
    @Query("SELECT avg(e.salary) FROM Employee e where e.position.id = :id")
    double returnAvgSalaryByPosition(@Param("id") Integer id);
    @Query("SELECT count(e) FROM Employee e where e.position.id = :id")
    int findAllByPositionIdCount(Integer id);
    @Query("SELECT COALESCE(sum(salary),0) FROM Employee")
    double returnSumSalary();
    @Query("SELECT e,p FROM Employee e JOIN FETCH Position p ON e.position = p WHERE e.salary > (SELECT avg(salary) from Employee)")
    List<Employee> getAboveAveragePaidEmployees();
    @Query("SELECT e,p FROM Employee e JOIN FETCH Position p ON e.position = p WHERE e.salary > :salary")
    List<Employee> findAllBySalaryIsBiggerThan(@Param("salary") double salary);
    @Query("SELECT e,p FROM Employee e JOIN FETCH Position p ON e.position = p WHERE e.id = :id")
    Optional<Employee> getEmployeeById(@Param("id") Integer id);
    @Query(value = "SELECT new ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo (e.name, e.salary, p.name) " +
            "FROM Employee e JOIN FETCH Position p ON e.position = p")
    List<EmployeesInfo> findAllEmployeesView();
    @Query(value = "SELECT new ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo (e.name, e.salary, p.name) " +
            "FROM Employee e JOIN FETCH Position p ON e.position = p WHERE e.id = :id")
    EmployeesInfo findEmployeeByIdView(@Param ("id") Integer id);
    @Query("SELECT e,p FROM Employee e JOIN FETCH Position p ON e.position = p WHERE e.position.name = :position")
    List<Employee> returnAllByPositionName(@Param("position") String position);
    @Query("SELECT e,p FROM Employee e JOIN FETCH Position p ON e.position = p WHERE e.position.id = :id")
    List<Employee> returnAllByPositionId(@Param("id") Integer id);
    @Query("SELECT e,p FROM Employee e JOIN FETCH Position p ON e.position = p")
    List<Employee> findAllEmployees();
    @Query("SELECT p FROM Position p WHERE p.name = :positionName")
    Position findPositionByName(@Param("positionName") String positionName);
}
