package ru.skypro.lessons.springboot.webLibrary.service.position;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.webLibrary.models.dto.PositionDTO;
import ru.skypro.lessons.springboot.webLibrary.repository.PositionRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
@AllArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;
    @Override
    public List<PositionDTO> findAll() {
        return StreamSupport.stream(positionRepository.findAll().spliterator(), false).map(PositionDTO::fromPosition).toList();
    }
}
