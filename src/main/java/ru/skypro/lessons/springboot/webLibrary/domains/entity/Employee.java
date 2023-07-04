package ru.skypro.lessons.springboot.webLibrary.domains.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.parameters.P;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "salary")
    private double salary;
    @Column(name = "name")
    private String name;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id")
    private Position position;

    public Employee(String name, double salary, Position position) {
        this.name = name;
        this.salary = salary;
        this.position = position;
    }
}
