package ru.skypro.lessons.springboot.webLibrary.models.mapper;

import ru.skypro.lessons.springboot.webLibrary.domains.entity.Position;
import ru.skypro.lessons.springboot.webLibrary.models.dto.PositionDTO;

public class PositionDTOMapper {
        public static Position toPosition(PositionDTO positionDTO){
            return Position.builder()
                    .id(positionDTO.getId())
                    .name(positionDTO.getName())
                    .build();
        }
        public static PositionDTO fromPosition(Position position){
            return PositionDTO.builder()
                    .id(position.getId())
                    .name(position.getName())
                    .build();
        }
    }