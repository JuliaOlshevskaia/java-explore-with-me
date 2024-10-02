package ru.practicum.mainservice.errors.exceptions;

public class EventValidationException extends RuntimeException {
    public EventValidationException(String message) {
        super(message);
    }
}
