package ru.skypro.lessons.springboot.webLibrary.service.position;

import ru.skypro.lessons.springboot.webLibrary.models.dto.PositionDTO;
import java.util.List;

public interface PositionService {
    List<PositionDTO> findAll();

}
