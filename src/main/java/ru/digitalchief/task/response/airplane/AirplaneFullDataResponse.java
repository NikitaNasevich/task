package ru.digitalchief.task.response.airplane;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirplaneFullDataResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    private long airplaneId;

    private String model;

    private int numberOfSeats;

    private boolean socket;

    private boolean media;

    private boolean wiFi;

    private int seatPitch;

    private int seatWidth;

    private boolean hasFirstClass;

    private String chairScheme;
}
