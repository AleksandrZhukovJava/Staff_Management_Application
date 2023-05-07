package ru.skypro.lessons.springboot.webLibrary.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee,Long>{
    @Query("select e FROM Employee e")
    public List<Employee> returnAllEmployee();
    @Query("SELECT e FROM Employee e where e.salary = (Select min(salary) from Employee)")
    List<Employee> returnMinSalaryEmployees();
    @Query("SELECT e FROM Employee e where e.salary = (Select max(salary) from Employee)")
    List<Employee> returnMaxSalaryEmployees();
    @Query("SELECT sum(salary) FROM Employee")
    double returnSumSalary();
    @Query("SELECT e FROM Employee e WHERE e.salary > (SELECT avg(salary) from Employee)")
    List<Employee> getAboveAveragePaidEmployees();

}
