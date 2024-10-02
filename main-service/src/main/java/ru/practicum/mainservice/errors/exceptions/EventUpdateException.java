package ru.practicum.mainservice.errors.exceptions;

public class EventUpdateException extends RuntimeException {
    public EventUpdateException(String message) {
        super(message);
    }
}
