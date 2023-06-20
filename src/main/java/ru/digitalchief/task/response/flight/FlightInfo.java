package ru.digitalchief.task.response.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class FlightInfo {
    private long flightId;
    private String departureCity;
    private String arrivalCity;
    private String flightNumber;
    private String departureDateTime;
    private String arrivalDateTime;
    private String airplaneModel;
    private boolean meal;
    private boolean handLuggage;
    private boolean luggage;
    private boolean socket;
    private boolean media;
    private boolean wiFi;
    private int seatPitch;
    private int seatWidth;
    private String chairScheme;
}
