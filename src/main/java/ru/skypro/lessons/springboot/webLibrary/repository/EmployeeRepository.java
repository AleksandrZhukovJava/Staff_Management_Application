package ru.skypro.lessons.springboot.webLibrary.repository;

import ru.skypro.lessons.springboot.webLibrary.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> returnAllEmployee();
}
