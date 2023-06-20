package ru.digitalchief.task.exeption.trip;

public class TripAlreadyExistsException extends RuntimeException {
    public TripAlreadyExistsException(String message) {
        super(message);
    }
}
