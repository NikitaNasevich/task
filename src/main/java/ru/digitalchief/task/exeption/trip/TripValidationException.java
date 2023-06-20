package ru.digitalchief.task.exeption.trip;

public class TripValidationException extends RuntimeException {
    public TripValidationException(String msg) {
        super(msg);
    }
}
