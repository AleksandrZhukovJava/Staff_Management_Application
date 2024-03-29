package ru.skypro.lessons.springboot.webLibrary.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.skypro.lessons.springboot.webLibrary.exceptions.customexceptions.IllegalIdException;

import java.io.IOException;
import java.sql.SQLException;


@RestControllerAdvice
public class WebLibraryExceptionHandler {

        private static final Logger logger = LoggerFactory.getLogger(WebLibraryExceptionHandler.class);

    @ExceptionHandler(value = {NumberFormatException.class,
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            IOException.class,
            JpaObjectRetrievalFailureException.class})
    public ResponseEntity<?> handleBadRequestException(Exception exception) {
        logger.error("Exception caught: " + exception);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleIllegalIdException(IllegalIdException exception) {
        logger.error("There is no employee with id = " + exception.getVariableValue(), exception);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleSQLException(SQLException sqlException) {
        logger.error("Exception caught: " + sqlException);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleMissingServletRequestParameterExceptionException(MissingServletRequestParameterException missingServletRequestParameterException) {
        logger.error("Exception caught: " + missingServletRequestParameterException);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleUnknownException(Exception exception) {
        logger.error("Exception caught: " + exception);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
