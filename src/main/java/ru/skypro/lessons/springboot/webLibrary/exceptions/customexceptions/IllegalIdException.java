package ru.skypro.lessons.springboot.webLibrary.exceptions.customexceptions;

public class IllegalIdException extends IllegalArgumentException{
    private final Integer variableValue;
    public IllegalIdException(Integer variableValue) {
        this.variableValue = variableValue;
    }
    public Integer getIdValue() {
        return variableValue;
    }
}