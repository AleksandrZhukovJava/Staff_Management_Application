package ru.skypro.lessons.springboot.webLibrary.domains.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "position")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Position {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;

    public Position(String name) {
        this.name = name;
    }
}


