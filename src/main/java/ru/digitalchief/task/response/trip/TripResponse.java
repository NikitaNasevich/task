package ru.digitalchief.task.response.trip;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {
    private String departureCity;
    private String arrivalCity;
}
