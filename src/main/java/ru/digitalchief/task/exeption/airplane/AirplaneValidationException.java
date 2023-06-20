package ru.digitalchief.task.exeption.airplane;

public class AirplaneValidationException extends RuntimeException {
    public AirplaneValidationException(String message) {
        super(message);
    }
}
