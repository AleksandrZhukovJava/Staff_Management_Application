package ru.skypro.lessons.springboot.webLibrary.utility;

import ru.skypro.lessons.springboot.webLibrary.domains.entity.Employee;

public class Validation {
    public static void modelValidation(Employee employee) throws IllegalArgumentException{
        if (employee.getId() <= 0 || employee.getName().isBlank() || employee.getSalary() < 0){
            throw new IllegalArgumentException();
        }
    }
    public static void modelValidation(int id) throws IllegalArgumentException{
        if (id <= 0){
            throw new IllegalArgumentException();
        }
    }
    public static void modelValidation(double salary) throws IllegalArgumentException{
        if (salary < 0){
            throw new IllegalArgumentException();
        }
    }

}
