package ru.skypro.lessons.springboot.webLibrary.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Position;

public interface PositionRepository extends CrudRepository<Position, Integer> {
}
