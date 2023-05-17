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
    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> getEmployeesWithSalaryMoreThan(@Param("salary") double salary);
    @Query("SELECT e FROM Employee e WHERE e.id = :id")
    Employee getEmployeeById(@Param("id") int id);
    @Modifying
    @Query(value = "DELETE FROM employees e WHERE e.id = :id", nativeQuery = true)
    void deleteEmployeeById(@Param("id") int id); //Знаю что есть стандартные методы, но хотелось попробовать самому написать
    @Modifying
    @Query(value = "UPDATE employees SET id = :id, name = :name, salary = :salary where id = :employeeId", nativeQuery = true)
    void changeEmployeeById(@Param("id") int id, @Param("name") String name, @Param("salary") double salary, @Param("employeeId") int employeeId);
}
