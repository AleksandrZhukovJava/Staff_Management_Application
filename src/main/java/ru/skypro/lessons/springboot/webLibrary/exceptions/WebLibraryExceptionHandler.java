package ru.skypro.lessons.springboot.webLibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;


@RestControllerAdvice
public class WebLibraryExceptionHandler {
    @ExceptionHandler(value = {NumberFormatException.class,
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            IOException.class,
            JpaObjectRetrievalFailureException.class})
    public ResponseEntity<?> handleNumberFormatException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleSQLException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
