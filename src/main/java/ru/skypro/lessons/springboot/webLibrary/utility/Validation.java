package ru.skypro.lessons.springboot.webLibrary.utility;

import ru.skypro.lessons.springboot.webLibrary.domains.entity.Employee;
import ru.skypro.lessons.springboot.webLibrary.exceptions.customexceptions.IllegalIdException;

public class Validation {
    public static void modelValidation(Employee employee) throws IllegalIdException {
        if (employee.getId() <= 0)
            throw new IllegalIdException(employee.getId());
        else if (employee.getName().isBlank() || employee.getSalary() < 0)
            throw new IllegalArgumentException();
    }
    public static void modelValidation(int id) throws IllegalIdException {
        if (id <= 0)
            throw new IllegalIdException(id);
    }

    public static void modelValidation(double salary) throws IllegalArgumentException {
        if (salary < 0)
            throw new IllegalArgumentException();
    }
}
