package ru.skypro.lessons.springboot.webLibrary.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.lessons.springboot.webLibrary.pojo.Position;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {
    private Integer id;
    private String name;

    public static PositionDTO fromPosition(Position employee) {
        return PositionDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .build();

    }

    public Position toPosition() {
        return new Position(this.id, this.name);
    }

}
