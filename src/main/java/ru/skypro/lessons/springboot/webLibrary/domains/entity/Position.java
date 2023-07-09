package ru.skypro.lessons.springboot.webLibrary.domains.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "position")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Position{
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


