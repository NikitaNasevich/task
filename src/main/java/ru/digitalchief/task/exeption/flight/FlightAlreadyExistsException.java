package ru.digitalchief.task.exeption.flight;

public class FlightAlreadyExistsException extends RuntimeException {
    public FlightAlreadyExistsException(String message) {
        super(message);
    }
}
