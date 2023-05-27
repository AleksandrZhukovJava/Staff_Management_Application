package ru.skypro.lessons.springboot.webLibrary.models.projections.projections;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeFullInfo { //target - не работает
    @Value("#{target.employee.id + ' ' + target.employee.salary + ' ' + target.employee.name + ' ' + target.position.name}")
    String getFullInfo();
}
