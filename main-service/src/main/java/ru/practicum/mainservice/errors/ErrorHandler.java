package ru.practicum.mainservice.errors;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainservice.errors.dto.ApiError;
import ru.practicum.mainservice.errors.exceptions.DataNotFoundException;
import ru.practicum.mainservice.errors.exceptions.EventUpdateException;
import ru.practicum.mainservice.errors.exceptions.EventValidationException;
import ru.practicum.mainservice.errors.exceptions.ValidationException;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Generated
@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(final ValidationException exception) {
        log.info("Данные не валидны {}", exception.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setReason("Incorrectly made request.");
        apiError.setStatus(HttpStatus.BAD_REQUEST.name());
        apiError.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleDataNotFoundException(final DataNotFoundException exception) {
        log.info("Данные не найдены {}", exception.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setReason("The required object was not found.");
        apiError.setStatus(HttpStatus.NOT_FOUND.name());
        apiError.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleEventUpdateException(final EventUpdateException exception) {
        log.info("Данные не валидны {}", exception.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setReason("For the requested operation the conditions are not met.");
        apiError.setStatus(HttpStatus.CONFLICT.name());
        apiError.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleConstraintViolationException(final DataIntegrityViolationException exception) {
        log.info("Данные не валидны {}", exception.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setReason("Integrity constraint has been violated.");
        apiError.setStatus(HttpStatus.CONFLICT.name());
        apiError.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleEventValidationException(final EventValidationException exception) {
        log.info("Данные не валидны {}", exception.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setReason("For the requested operation the conditions are not met.");
        apiError.setStatus(HttpStatus.CONFLICT.name());
        apiError.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(final RuntimeException exception) {
        log.info("Ошибка: {}", exception.getMessage());
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setReason("");
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        apiError.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<Object>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
