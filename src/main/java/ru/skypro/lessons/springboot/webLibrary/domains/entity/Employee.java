package ru.skypro.lessons.springboot.webLibrary.domains.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
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
    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "position_id")
    private Position position;

    public Employee(String name, double salary, Position position) {
        this.name = name;
        this.salary = salary;
        this.position = position;
    }
}
