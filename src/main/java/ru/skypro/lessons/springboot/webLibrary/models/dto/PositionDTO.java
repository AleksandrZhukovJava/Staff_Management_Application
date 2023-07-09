package ru.skypro.lessons.springboot.webLibrary.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {
    private Integer id;
    private String name;

}
