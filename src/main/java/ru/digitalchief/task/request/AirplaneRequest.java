package ru.digitalchief.task.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AirplaneRequest {
    @NotNull
    @NotEmpty
    private String model;

    @Min(value = 10)
    @Max(value = 500)
    private Integer numberOfSeats;

    @NotNull
    private Boolean socket;

    @NotNull
    private Boolean media;

    @NotNull
    private Boolean wiFi;

    @Min(value = 20)
    @Max(value = 200)
    private Integer seatPitch;

    @Min(value = 20)
    @Max(value = 200)
    private Integer seatWidth;

    @NotNull
    private Boolean hasFirstClass;

    @NotNull
    @NotEmpty
    private String chairScheme;
}
