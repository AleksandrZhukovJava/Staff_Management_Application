package ru.skypro.lessons.springboot.webLibrary.service.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.lessons.springboot.webLibrary.models.dto.PositionDTO;
import ru.skypro.lessons.springboot.webLibrary.models.mapper.PositionDTOMapper;
import ru.skypro.lessons.springboot.webLibrary.repository.PositionRepository;

import java.util.List;

import static constants.TestConstances.POSITIONS_TEST_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PositionServiceImplTest {

    @Mock
    private PositionRepository positionRepositoryMock;
    @InjectMocks
    private PositionServiceImpl out;

    @DisplayName("Return all position, convert to DTO and call repository once")
    @Test
    void findAll_ShouldReturnAllPositions_ShouldCallRepositoryOnce() {
        when(positionRepositoryMock.findAll())
                .thenReturn(POSITIONS_TEST_LIST);

        List<PositionDTO> actual = out.findAll();
        List<PositionDTO> expected = POSITIONS_TEST_LIST.stream().map(PositionDTOMapper::fromPosition).toList();

        assertEquals(expected, actual);

        verify(positionRepositoryMock, times(1)).findAll();
    }
}
