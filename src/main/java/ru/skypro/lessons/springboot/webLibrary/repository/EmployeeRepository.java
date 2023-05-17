package ru.skypro.lessons.springboot.webLibrary.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e where e.salary = (Select min(salary) from Employee)")
    List<Employee> returnMinSalaryEmployees();
    @Query("SELECT e FROM Employee e where e.salary = (Select max(salary) from Employee)")
    List<Employee> returnMaxSalaryEmployees();
    @Query("SELECT sum(salary) FROM Employee")
    double returnSumSalary();
    @Query("SELECT e FROM Employee e WHERE e.salary > (SELECT avg(salary) from Employee)")
    List<Employee> getAboveAveragePaidEmployees();
    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> getEmployeesWithSalaryMoreThan(@Param("salary") double salary);
}
