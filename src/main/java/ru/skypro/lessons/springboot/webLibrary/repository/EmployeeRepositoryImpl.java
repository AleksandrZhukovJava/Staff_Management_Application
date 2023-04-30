package ru.skypro.lessons.springboot.webLibrary.repository;


import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.webLibrary.config.HibernateSessionUtil;
import ru.skypro.lessons.springboot.webLibrary.model.Employee;
import java.util.List;
@Repository
@AllArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Session session = HibernateSessionUtil.getSessionFactory().openSession();

    @Override
    public List<Employee> returnAllEmployee() {
        return session.createQuery("from Employee", Employee.class).getResultList();
    }

    @Override
    public Employee returnEmployeeByID(Integer id) {
        return session.get(Employee.class, id);
    }

    public List<Employee> returnMinSalaryEmployees() {
        return session.createQuery("FROM Employee where salary = (Select min(salary) from Employee)", Employee.class).getResultList();
    }

    @Override
    public List<Employee> returnMaxSalaryEmployees() {
        return session.createQuery("FROM Employee where salary = (Select max(salary) from Employee)", Employee.class).getResultList();
    }

    @Override
    public double returnSumSalary() {
        return session.createQuery("SELECT sum(salary) FROM Employee", Double.class).getSingleResult();
    }

    @Override
    public List<Employee> getAboveAveragePaidEmployees() {
        return session.createQuery("FROM Employee WHERE salary > (SELECT avg(salary) from Employee)", Employee.class).getResultList();
    }
}
