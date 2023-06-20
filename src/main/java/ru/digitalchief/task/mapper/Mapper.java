package ru.digitalchief.task.mapper;

import org.springframework.stereotype.Component;
import ru.digitalchief.task.entity.Airplane;
import ru.digitalchief.task.entity.Flight;
import ru.digitalchief.task.entity.Trip;
import ru.digitalchief.task.request.AirplaneRequest;
import ru.digitalchief.task.request.FlightRequest;
import ru.digitalchief.task.request.TripRequest;
import ru.digitalchief.task.response.airplane.AirplaneFullDataResponse;
import ru.digitalchief.task.response.flight.FlightFullDataResponse;
import ru.digitalchief.task.response.flight.FlightInfo;
import ru.digitalchief.task.response.flight.FlightResponse;
import ru.digitalchief.task.response.trip.TripFullDataResponse;
import ru.digitalchief.task.response.trip.TripResponse;
import ru.digitalchief.task.util.time.TimeFormatter;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class Mapper {
    public FlightResponse mapFlightToFlightResponse(@NotNull Flight flight) {
        return FlightResponse.builder()
                .flightId(flight.getFlightId())
                .departureCity(flight.getTrip().getDepartureCity())
                .arrivalCity(flight.getTrip().getArrivalCity())
                .flightNumber(flight.getFlightNumber())
                .departureDateTime(TimeFormatter.formatLocalDateTimeToString(flight.getDepartureDateTime()))
                .arrivalDateTime(TimeFormatter.formatLocalDateTimeToString(flight.getArrivalDateTime()))
                .build();
    }

    public FlightInfo mapFlightToFlightInfo(@NotNull Flight flight) {
        return FlightInfo.builder()
                .flightId(flight.getFlightId())
                .departureCity(flight.getTrip().getDepartureCity())
                .arrivalCity(flight.getTrip().getArrivalCity())
                .flightNumber(flight.getFlightNumber())
                .departureDateTime(TimeFormatter.formatLocalDateTimeToString(flight.getDepartureDateTime()))
                .arrivalDateTime(TimeFormatter.formatLocalDateTimeToString(flight.getArrivalDateTime()))
                .airplaneModel(flight.getAirplane().getModel())
                .meal(flight.isMeal())
                .handLuggage(flight.isHandLuggage())
                .luggage(flight.isLuggage())
                .socket(flight.getAirplane().isSocket())
                .media(flight.getAirplane().isMedia())
                .wiFi(flight.getAirplane().isWiFi())
                .seatPitch(flight.getAirplane().getSeatPitch())
                .seatWidth(flight.getAirplane().getSeatWidth())
                .chairScheme(flight.getAirplane().getChairScheme())
                .build();
    }

    public Flight mapFlightRequestToFlight(@NotNull FlightRequest flightRequest,
                                           @NotNull Trip trip,
                                           @NotNull Airplane airplane) {
        LocalDateTime departureDateTime = TimeFormatter.formatStringToDateTime(flightRequest.getDepartureDateTime());
        LocalDateTime arrivalDateTime = TimeFormatter.formatStringToDateTime(flightRequest.getArrivalDateTime());

        return Flight.builder()
                .trip(trip)
                .airplane(airplane)
                .flightNumber(flightRequest.getFlightNumber())
                .departureDateTime(departureDateTime)
                .arrivalDateTime(arrivalDateTime)
                .meal(flightRequest.isMeal())
                .handLuggage(flightRequest.isHandLuggage())
                .luggage(flightRequest.isLuggage())
                .duration(Duration.between(departureDateTime, arrivalDateTime).toMinutes())
                .canceled(flightRequest.isCanceled())
                .build();
    }

    public FlightFullDataResponse mapFlightToFlightFullDataResponse(@NotNull Flight flight) {
        return FlightFullDataResponse.builder()
                .flightId(flight.getFlightId())
                .trip(flight.getTrip())
                .airplane(flight.getAirplane())
                .flightNumber(flight.getFlightNumber())
                .departureDateTime(flight.getDepartureDateTime())
                .arrivalDateTime(flight.getArrivalDateTime())
                .duration(flight.getDuration())
                .meal(flight.isMeal())
                .handLuggage(flight.isHandLuggage())
                .luggage(flight.isLuggage())
                .build();
    }

    public Trip mapTripRequestToTrip(@NotNull TripRequest tripRequest) {
        return Trip.builder()
                .departureCity(tripRequest.getDepartureCity())
                .arrivalCity(tripRequest.getArrivalCity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public TripFullDataResponse mapTripToTripFullDataResponse(@NotNull Trip trip) {
        return TripFullDataResponse.builder()
                .tripId(trip.getTripId())
                .departureCity(trip.getDepartureCity())
                .arrivalCity(trip.getArrivalCity())
                .createdAt(TimeFormatter.formatLocalDateTimeToString(trip.getCreatedAt()))
                .updatedAt(TimeFormatter.formatLocalDateTimeToString(trip.getUpdatedAt()))
                .build();
    }

    public TripResponse mapTripToTripResponse(@NotNull Trip trip) {
        return TripResponse.builder()
                .departureCity(trip.getDepartureCity())
                .arrivalCity(trip.getArrivalCity())
                .build();
    }

    public Airplane mapAirplaneRequestToAirplane(@NotNull AirplaneRequest airplaneRequest) {
        return Airplane.builder()
                .model(airplaneRequest.getModel())
                .numberOfSeats(airplaneRequest.getNumberOfSeats())
                .socket(airplaneRequest.getSocket())
                .media(airplaneRequest.getMedia())
                .wiFi(airplaneRequest.getWiFi())
                .seatPitch(airplaneRequest.getSeatPitch())
                .seatWidth(airplaneRequest.getSeatWidth())
                .hasFirstClass(airplaneRequest.getHasFirstClass())
                .chairScheme(airplaneRequest.getChairScheme())
                .build();
    }

    public AirplaneFullDataResponse mapAirplaneToAirplaneFullDataResponse(Airplane airplane) {
        return AirplaneFullDataResponse.builder()
                .airplaneId(airplane.getAirplaneId())
                .model(airplane.getModel())
                .numberOfSeats(airplane.getNumberOfSeats())
                .socket(airplane.isSocket())
                .media(airplane.isMedia())
                .wiFi(airplane.isWiFi())
                .seatPitch(airplane.getSeatPitch())
                .seatWidth(airplane.getSeatWidth())
                .hasFirstClass(airplane.isHasFirstClass())
                .chairScheme(airplane.getChairScheme())
                .build();
    }
}
