package ru.skypro.lessons.springboot.webLibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice
public class EmployeeExceptionHandler {
    @ExceptionHandler(value = {NumberFormatException.class, IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleNumberFormatException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleSQLException(Exception exception) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
