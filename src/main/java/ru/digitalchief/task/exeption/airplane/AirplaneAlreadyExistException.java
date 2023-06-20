package ru.digitalchief.task.exeption.airplane;

public class AirplaneAlreadyExistException extends RuntimeException {
    public AirplaneAlreadyExistException(String message) {
        super(message);
    }
}
