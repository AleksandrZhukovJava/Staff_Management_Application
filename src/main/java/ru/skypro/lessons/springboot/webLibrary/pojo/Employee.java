package ru.skypro.lessons.springboot.webLibrary.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "salary")
    private double salary;
    @Column(name = "name")
    private String name;
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id")
    private Position position;
}
