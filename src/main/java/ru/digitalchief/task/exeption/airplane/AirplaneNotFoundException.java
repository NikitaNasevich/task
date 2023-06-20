package ru.digitalchief.task.exeption.airplane;

public class AirplaneNotFoundException extends RuntimeException {
    public AirplaneNotFoundException(String message) {
        super(message);
    }
}
