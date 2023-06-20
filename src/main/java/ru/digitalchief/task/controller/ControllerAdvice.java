package ru.digitalchief.task.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.digitalchief.task.exeption.ErrorResponse;
import ru.digitalchief.task.exeption.ParseException;
import ru.digitalchief.task.exeption.airplane.AirplaneNotFoundException;
import ru.digitalchief.task.exeption.flight.FlightAlreadyExistsException;
import ru.digitalchief.task.exeption.flight.FlightNotFoundException;
import ru.digitalchief.task.exeption.flight.FlightValidationException;
import ru.digitalchief.task.exeption.trip.TripAlreadyExistsException;
import ru.digitalchief.task.exeption.trip.TripNotFoundException;
import ru.digitalchief.task.exeption.trip.TripValidationException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(TripValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleTripNotCreatedException(TripValidationException tripValidationException) {
        log.error(tripValidationException.getMessage());
        return new ErrorResponse(tripValidationException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(TripNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleTripNotFoundException(TripNotFoundException tripNotFoundException) {
        log.error(tripNotFoundException.getMessage());
        return new ErrorResponse(tripNotFoundException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(TripAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ResponseBody
    public ErrorResponse handleTripAlreadyExistsException(TripAlreadyExistsException tripAlreadyExistsException) {
        log.error(tripAlreadyExistsException.getMessage());
        return new ErrorResponse(tripAlreadyExistsException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AirplaneNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleAirplaneNotFoundException(AirplaneNotFoundException airplaneNotFoundException) {
        log.error(airplaneNotFoundException.getMessage());
        return new ErrorResponse(airplaneNotFoundException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(FlightValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleFlightNotCreatedException(FlightValidationException flightValidationException) {
        log.error(flightValidationException.getMessage());
        return new ErrorResponse(flightValidationException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(FlightNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleFlightNotFoundException(FlightNotFoundException flightNotFoundException) {
        log.error(flightNotFoundException.getMessage());
        return new ErrorResponse(flightNotFoundException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(FlightAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ResponseBody
    public ErrorResponse handleFlightAlreadyExistsException(FlightAlreadyExistsException flightAlreadyExistsException) {
        log.error(flightAlreadyExistsException.getMessage());
        return new ErrorResponse(flightAlreadyExistsException.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleParseException(ParseException parseException) {
        log.error(parseException.getMessage());
        return new ErrorResponse(parseException.getMessage(), LocalDateTime.now());
    }
}
