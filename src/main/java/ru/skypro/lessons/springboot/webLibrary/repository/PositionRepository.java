package ru.skypro.lessons.springboot.webLibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
