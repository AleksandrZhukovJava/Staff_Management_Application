package ru.skypro.lessons.springboot.webLibrary.exceptions.customexceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IllegalIdException extends IllegalArgumentException{
    private Integer variableValue;
}