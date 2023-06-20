package ru.digitalchief.task.response.airplane;

import lombok.Data;

import java.util.List;

@Data
public class ListAirplaneFullDataResponse {
    private int total;
    private List<AirplaneFullDataResponse> airplanes;
}
