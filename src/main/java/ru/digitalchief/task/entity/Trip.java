package ru.digitalchief.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
@AllArgsConstructor
@Entity(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private long tripId;

    @Column(name = "departure_city")
    @NotEmpty(message = "Departure city shouldn't be empty")
    @NotNull(message = "Departure city shouldn't be null")
    private String departureCity;

    @Column(name = "arrival_city")
    @NotEmpty(message = "Arrival city shouldn't be empty")
    @NotNull(message = "Arrival city shouldn't be null")
    private String arrivalCity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonBackReference
    @OneToMany(mappedBy = "trip")
    private List<Flight> flights;
}
