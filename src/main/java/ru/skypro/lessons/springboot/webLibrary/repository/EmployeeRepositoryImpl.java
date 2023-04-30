package ru.skypro.lessons.springboot.webLibrary.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;

import java.util.ArrayList;
import java.util.List;
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{
    private final List<Employee> dataBaseImitationList = new ArrayList<>(List.of(
            new Employee("Employee1",20000),
            new Employee("Employee2",70000),
            new Employee("Employee3",20000),
            new Employee("Employee4",15000),
            new Employee("FalsePoorGuy",2000),
            new Employee("FalseRichGuy",200000),
            new Employee("RichGuy",200000),
            new Employee("PoorGuy",2000)
    ));
    @Override
    public List<Employee> returnAllEmployee() {
        return dataBaseImitationList;
    }
}
