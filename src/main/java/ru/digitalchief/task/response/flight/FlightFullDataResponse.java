package ru.digitalchief.task.response.flight;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.digitalchief.task.entity.Airplane;
import ru.digitalchief.task.entity.Trip;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightFullDataResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    private long flightId;
    private Trip trip;
    private Airplane airplane;
    private String flightNumber;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private long duration;
    private boolean meal;
    private boolean handLuggage;
    private boolean luggage;
}
