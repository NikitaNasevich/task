package ru.digitalchief.task.response.trip;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripFullDataResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    private long tripId;
    private String departureCity;
    private String arrivalCity;
    private String createdAt;
    private String updatedAt;
}
